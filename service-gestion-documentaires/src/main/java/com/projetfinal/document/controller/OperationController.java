package com.projetfinal.document.controller;

import com.ariani.servicegestionfondsdocumentaires.model.Operation;
import com.ariani.servicegestionfondsdocumentaires.service.CatalogueService;
import com.ariani.servicegestionfondsdocumentaires.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;
    private final CatalogueService catalogueService;

    @GetMapping
    public ResponseEntity<List<Operation>> getAllOperations() {
        return ResponseEntity.ok(operationService.getAllOperations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operation> getOperationById(@PathVariable Long id) {
        return operationService.getOperationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usager/{usagerId}")
    public ResponseEntity<List<Operation>> getOperationsByUsager(@PathVariable Long usagerId) {
        return ResponseEntity.ok(operationService.getOperationsByUsager(usagerId));
    }

    @GetMapping("/usager/{usagerId}/actives")
    public ResponseEntity<List<Operation>> getOperationsActivesByUsager(@PathVariable Long usagerId) {
        return ResponseEntity.ok(operationService.getOperationsActivesByUsager(usagerId));
    }

    @GetMapping("/ouvrage/{ouvrageId}")
    public ResponseEntity<List<Operation>> getOperationsByOuvrage(@PathVariable Long ouvrageId) {
        return catalogueService.getOuvrageById(ouvrageId)
                .map(ouvrage -> ResponseEntity.ok(operationService.getOperationsByOuvrage(ouvrage)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ouvrage/{ouvrageId}/actives")
    public ResponseEntity<List<Operation>> getOperationsActivesByOuvrage(@PathVariable Long ouvrageId) {
        return catalogueService.getOuvrageById(ouvrageId)
                .map(ouvrage -> ResponseEntity.ok(operationService.getOperationsActivesByOuvrage(ouvrage)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/emprunter")
    public ResponseEntity<?> emprunterOuvrage(@RequestBody Map<String, Long> request) {
        Long usagerId = request.get("usagerId");
        Long ouvrageId = request.get("ouvrageId");
        
        return catalogueService.getOuvrageById(ouvrageId)
                .map(ouvrage -> {
                    Operation operation = operationService.emprunterOuvrage(usagerId, ouvrage);
                    if (operation != null) {
                        return ResponseEntity.status(HttpStatus.CREATED).body(operation);
                    } else {
                        return ResponseEntity.badRequest().build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/reserver")
    public ResponseEntity<Operation> reserverOuvrage(@RequestBody Map<String, Long> request) {
        Long usagerId = request.get("usagerId");
        Long ouvrageId = request.get("ouvrageId");
        
        return catalogueService.getOuvrageById(ouvrageId)
                .map(ouvrage -> {
                    Operation operation = operationService.reserverOuvrage(usagerId, ouvrage);
                    return ResponseEntity.status(HttpStatus.CREATED).body(operation);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/retourner/{operationId}")
    public ResponseEntity<Boolean> retournerOuvrage(@PathVariable Long operationId) {
        boolean success = operationService.retournerOuvrage(operationId);
        if (success) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PostMapping("/annuler-reservation/{operationId}")
    public ResponseEntity<Boolean> annulerReservation(@PathVariable Long operationId) {
        boolean success = operationService.annulerReservation(operationId);
        if (success) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }
} 