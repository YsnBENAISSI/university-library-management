package com.projetfinal.document.repository;

import com.ariani.servicegestionfondsdocumentaires.model.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {
    Optional<Catalogue> findByIsbn(String isbn);
} 