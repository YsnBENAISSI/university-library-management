package com.projetfinal.document.controller;

import com.ariani.servicegestionfondsdocumentaires.model.Catalogue;
import com.ariani.servicegestionfondsdocumentaires.service.CatalogueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogue")
@RequiredArgsConstructor
public class CatalogueController {

    private final CatalogueService catalogueService;

    @GetMapping
    public ResponseEntity<List<Catalogue>> getAllOuvrages() {
        return ResponseEntity.ok(catalogueService.getAllOuvrages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Catalogue> getOuvrageById(@PathVariable Long id) {
        return catalogueService.getOuvrageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Catalogue> getOuvrageByIsbn(@PathVariable String isbn) {
        return catalogueService.getOuvrageByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Catalogue> createOuvrage(@RequestBody Catalogue catalogue) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(catalogueService.saveOuvrage(catalogue));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Catalogue> updateOuvrage(@PathVariable Long id, @RequestBody Catalogue catalogue) {
        return catalogueService.getOuvrageById(id)
                .map(existingOuvrage -> {
                    catalogue.setId(id);
                    return ResponseEntity.ok(catalogueService.saveOuvrage(catalogue));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOuvrage(@PathVariable Long id) {
        catalogueService.deleteOuvrage(id);
        return ResponseEntity.noContent().build();
    }
} 