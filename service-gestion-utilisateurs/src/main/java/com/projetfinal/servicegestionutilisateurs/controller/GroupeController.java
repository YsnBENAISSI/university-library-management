package com.projetfinal.servicegestionutilisateurs.controller;

import lombok.RequiredArgsConstructor;
import ma.ariani.servicegestionutilisateurs.model.Groupe;
import ma.ariani.servicegestionutilisateurs.service.GroupeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
@RequiredArgsConstructor
public class GroupeController {

    private final GroupeService groupeService;

    @GetMapping
    public ResponseEntity<List<Groupe>> getAllGroupes() {
        return ResponseEntity.ok(groupeService.getAllGroupes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Groupe> getGroupeById(@PathVariable Long id) {
        return groupeService.getGroupeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Groupe> createGroupe(@RequestBody Groupe groupe) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(groupeService.saveGroupe(groupe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Groupe> updateGroupe(@PathVariable Long id, @RequestBody Groupe groupe) {
        return groupeService.getGroupeById(id)
                .map(existingGroupe -> {
                    groupe.setId(id);
                    return ResponseEntity.ok(groupeService.saveGroupe(groupe));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupe(@PathVariable Long id) {
        groupeService.deleteGroupe(id);
        return ResponseEntity.noContent().build();
    }
} 