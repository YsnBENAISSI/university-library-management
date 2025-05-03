package com.projetfinal.document.service;

import com.ariani.servicegestionfondsdocumentaires.model.Catalogue;
import com.ariani.servicegestionfondsdocumentaires.model.Operation;
import com.ariani.servicegestionfondsdocumentaires.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;
    private final FondService fondService;

    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    public Optional<Operation> getOperationById(Long id) {
        return operationRepository.findById(id);
    }

    public List<Operation> getOperationsByUsager(Long usagerId) {
        return operationRepository.findByUsagerId(usagerId);
    }

    public List<Operation> getOperationsByOuvrage(Catalogue ouvrage) {
        return operationRepository.findByOuvrage(ouvrage);
    }

    public List<Operation> getOperationsActivesByOuvrage(Catalogue ouvrage) {
        return operationRepository.findByOuvrageAndActifTrue(ouvrage);
    }

    public List<Operation> getOperationsActivesByUsager(Long usagerId) {
        return operationRepository.findByUsagerIdAndActifTrue(usagerId);
    }

    public Operation saveOperation(Operation operation) {
        return operationRepository.save(operation);
    }

    public void deleteOperation(Long id) {
        operationRepository.deleteById(id);
    }

    public Operation emprunterOuvrage(Long usagerId, Catalogue ouvrage) {
        // Vérifier la disponibilité de l'ouvrage
        if (fondService.isOuvrageDisponible(ouvrage)) {
            // Décrémenter le nombre d'exemplaires disponibles
            fondService.decrementerExemplaires(ouvrage);
            
            // Créer une opération d'emprunt
            Operation operation = new Operation();
            operation.setUsagerId(usagerId);
            operation.setOuvrage(ouvrage);
            operation.setType(Operation.TypeOperation.EMPRUNT);
            operation.setDateDebut(LocalDate.now());
            operation.setDateFin(LocalDate.now().plusDays(8)); // 8 jours d'emprunt
            operation.setActif(true);
            
            return operationRepository.save(operation);
        }
        
        return null;
    }

    public Operation reserverOuvrage(Long usagerId, Catalogue ouvrage) {
        // Créer une opération de réservation
        Operation operation = new Operation();
        operation.setUsagerId(usagerId);
        operation.setOuvrage(ouvrage);
        operation.setType(Operation.TypeOperation.RESERVATION);
        operation.setDateDebut(LocalDate.now());
        operation.setDateFin(LocalDate.now().plusDays(8)); // 8 jours de réservation
        operation.setActif(true);
        
        return operationRepository.save(operation);
    }

    public boolean retournerOuvrage(Long operationId) {
        Optional<Operation> operationOpt = operationRepository.findById(operationId);
        
        if (operationOpt.isPresent()) {
            Operation operation = operationOpt.get();
            
            if (operation.getType() == Operation.TypeOperation.EMPRUNT && operation.getActif()) {
                // Marquer l'opération comme inactive
                operation.setActif(false);
                operationRepository.save(operation);
                
                // Incrémenter le nombre d'exemplaires disponibles
                fondService.incrementerExemplaires(operation.getOuvrage());
                
                return true;
            }
        }
        
        return false;
    }

    public boolean annulerReservation(Long operationId) {
        Optional<Operation> operationOpt = operationRepository.findById(operationId);
        
        if (operationOpt.isPresent()) {
            Operation operation = operationOpt.get();
            
            if (operation.getType() == Operation.TypeOperation.RESERVATION && operation.getActif()) {
                // Marquer l'opération comme inactive
                operation.setActif(false);
                operationRepository.save(operation);
                
                return true;
            }
        }
        
        return false;
    }
} 