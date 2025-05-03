package com.projetfinal.servicegestionutilisateurs.controller;

import lombok.RequiredArgsConstructor;
import ma.ariani.servicegestionutilisateurs.model.Usager;
import ma.ariani.servicegestionutilisateurs.service.UsagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usagers")
@RequiredArgsConstructor
public class UsagerController {

    private final UsagerService usagerService;

    @GetMapping
    public ResponseEntity<List<Usager>> getAllUsagers() {
        return ResponseEntity.ok(usagerService.getAllUsagers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usager> getUsagerById(@PathVariable Long id) {
        return usagerService.getUsagerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usager> createUsager(@RequestBody Usager usager) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usagerService.saveUsager(usager));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usager> updateUsager(@PathVariable Long id, @RequestBody Usager usager) {
        return usagerService.getUsagerById(id)
                .map(existingUsager -> {
                    usager.setId(id);
                    return ResponseEntity.ok(usagerService.saveUsager(usager));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsager(@PathVariable Long id) {
        usagerService.deleteUsager(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticate(@RequestBody Map<String, String> credentials) {
        String login = credentials.get("login");
        String motDePasse = credentials.get("motDePasse");
        
        return ResponseEntity.ok(usagerService.authenticate(login, motDePasse));
    }
} 