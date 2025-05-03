package com.projetfinal.document.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class JwtConsumerController {
    
    private final String usagersServiceUrl = "http://localhost:8081/api/auth";
    private final RestTemplate restTemplate = new RestTemplate();
    
    @PostMapping("/login")
    public ResponseEntity<?> loginWithUsagersService(@RequestBody Map<String, String> credentials) {
        try {
            // Transmet la requête au service de gestion des utilisateurs
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    usagersServiceUrl + "/login", 
                    credentials,
                    Map.class
            );
            
            // Retourne la réponse au client
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de l'authentification: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
} 