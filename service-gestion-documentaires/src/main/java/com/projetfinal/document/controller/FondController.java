package com.projetfinal.document.controller;

import com.ariani.servicegestionfondsdocumentaires.model.Fond;
import com.ariani.servicegestionfondsdocumentaires.service.CatalogueService;
import com.ariani.servicegestionfondsdocumentaires.service.FondService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fonds")
@RequiredArgsConstructor
public class FondController {

    private final FondService fondService;
    private final CatalogueService catalogueService;

    @GetMapping
    public ResponseEntity<List<Fond>> getAllFonds() {
        return ResponseEntity.ok(fondService.getAllFonds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fond> getFondById(@PathVariable Long id) {
        return fondService.getFondById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ouvrage/{ouvrageId}")
    public ResponseEntity<Fond> getFondByOuvrage(@PathVariable Long ouvrageId) {
        return catalogueService.getOuvrageById(ouvrageId)
                .flatMap(ouvrage -> fondService.getFondByOuvrage(ouvrage))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Fond> createFond(@RequestBody Fond fond) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fondService.saveFond(fond));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fond> updateFond(@PathVariable Long id, @RequestBody Fond fond) {
        return fondService.getFondById(id)
                .map(existingFond -> {
                    fond.setId(id);
                    return ResponseEntity.ok(fondService.saveFond(fond));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFond(@PathVariable Long id) {
        fondService.deleteFond(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponible/{ouvrageId}")
    public ResponseEntity<Boolean> isOuvrageDisponible(@PathVariable Long ouvrageId) {
        return catalogueService.getOuvrageById(ouvrageId)
                .map(ouvrage -> ResponseEntity.ok(fondService.isOuvrageDisponible(ouvrage)))
                .orElse(ResponseEntity.notFound().build());
    }
}