package com.projetfinal.servicegestionutilisateurs.controller;

import ma.ariani.servicegestionutilisateurs.model.Usager;
import ma.ariani.servicegestionutilisateurs.service.JwtService;
import ma.ariani.servicegestionutilisateurs.service.UsagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsagerService usagerService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UsagerService usagerService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usagerService = usagerService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String login = credentials.get("login");
        String motDePasse = credentials.get("motDePasse");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, motDePasse)
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
        }

        // L'authentification a réussi
        Usager usager = usagerService.getUsagerByLogin(login).orElseThrow();
        String token = jwtService.generateToken(usager);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "Authentification réussie");
        response.put("usager", Map.of(
            "id", usager.getId(),
            "login", usager.getLogin(),
            "nom", usager.getNom(),
            "prenom", usager.getPrenom()
        ));

        return ResponseEntity.ok(response);
    }
} 