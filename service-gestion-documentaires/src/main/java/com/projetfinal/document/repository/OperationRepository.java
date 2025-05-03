package com.projetfinal.document.repository;

import com.ariani.servicegestionfondsdocumentaires.model.Catalogue;
import com.ariani.servicegestionfondsdocumentaires.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByUsagerId(Long usagerId);
    List<Operation> findByOuvrage(Catalogue ouvrage);
    List<Operation> findByOuvrageAndActifTrue(Catalogue ouvrage);
    List<Operation> findByUsagerIdAndActifTrue(Long usagerId);
    List<Operation> findByType(Operation.TypeOperation type);
} 