package com.projetfinal.document.service;

import com.ariani.servicegestionfondsdocumentaires.model.Catalogue;
import com.ariani.servicegestionfondsdocumentaires.repository.CatalogueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogueService {

    private final CatalogueRepository catalogueRepository;

    public List<Catalogue> getAllOuvrages() {
        return catalogueRepository.findAll();
    }

    public Optional<Catalogue> getOuvrageById(Long id) {
        return catalogueRepository.findById(id);
    }

    public Optional<Catalogue> getOuvrageByIsbn(String isbn) {
        return catalogueRepository.findByIsbn(isbn);
    }

    public Catalogue saveOuvrage(Catalogue catalogue) {
        return catalogueRepository.save(catalogue);
    }

    public void deleteOuvrage(Long id) {
        catalogueRepository.deleteById(id);
    }
} 