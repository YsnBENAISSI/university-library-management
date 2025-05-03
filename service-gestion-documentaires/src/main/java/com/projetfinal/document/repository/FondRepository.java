package com.projetfinal.document.repository;

import com.ariani.servicegestionfondsdocumentaires.model.Catalogue;
import com.ariani.servicegestionfondsdocumentaires.model.Fond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FondRepository extends JpaRepository<Fond, Long> {
    Optional<Fond> findByOuvrage(Catalogue ouvrage);
} 