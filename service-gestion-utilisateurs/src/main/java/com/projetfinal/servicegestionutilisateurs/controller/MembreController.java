package com.projetfinal.servicegestionutilisateurs.controller;

import lombok.RequiredArgsConstructor;
import ma.ariani.servicegestionutilisateurs.model.Membre;
import ma.ariani.servicegestionutilisateurs.service.GroupeService;
import ma.ariani.servicegestionutilisateurs.service.MembreService;
import ma.ariani.servicegestionutilisateurs.service.UsagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membres")
@RequiredArgsConstructor
public class MembreController {

    private final MembreService membreService;
    private final UsagerService usagerService;
    private final GroupeService groupeService;

    @GetMapping
    public ResponseEntity<List<Membre>> getAllMembres() {
        return ResponseEntity.ok(membreService.getAllMembres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membre> getMembreById(@PathVariable Long id) {
        return membreService.getMembreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usager/{usagerId}")
    public ResponseEntity<List<Membre>> getMembresByUsager(@PathVariable Long usagerId) {
        return usagerService.getUsagerById(usagerId)
                .map(usager -> ResponseEntity.ok(membreService.getMembresByUsager(usager)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/groupe/{groupeId}")
    public ResponseEntity<List<Membre>> getMembresByGroupe(@PathVariable Long groupeId) {
        return groupeService.getGroupeById(groupeId)
                .map(groupe -> ResponseEntity.ok(membreService.getMembresByGroupe(groupe)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Membre> createMembre(@RequestBody Membre membre) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(membreService.saveMembre(membre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembre(@PathVariable Long id) {
        membreService.deleteMembre(id);
        return ResponseEntity.noContent().build();
    }
} 