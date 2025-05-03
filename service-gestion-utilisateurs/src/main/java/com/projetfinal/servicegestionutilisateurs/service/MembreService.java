package com.projetfinal.servicegestionutilisateurs.service;

import lombok.RequiredArgsConstructor;
import ma.ariani.servicegestionutilisateurs.model.Groupe;
import ma.ariani.servicegestionutilisateurs.model.Membre;
import ma.ariani.servicegestionutilisateurs.model.Usager;
import ma.ariani.servicegestionutilisateurs.repository.MembreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembreService {

    private final MembreRepository membreRepository;

    public List<Membre> getAllMembres() {
        return membreRepository.findAll();
    }

    public Optional<Membre> getMembreById(Long id) {
        return membreRepository.findById(id);
    }

    public List<Membre> getMembresByUsager(Usager usager) {
        return membreRepository.findByUsager(usager);
    }

    public List<Membre> getMembresByGroupe(Groupe groupe) {
        return membreRepository.findByGroupe(groupe);
    }

    public Membre saveMembre(Membre membre) {
        return membreRepository.save(membre);
    }

    public void deleteMembre(Long id) {
        membreRepository.deleteById(id);
    }
} 