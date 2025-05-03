package com.projetfinal.servicegestionutilisateurs.service;

import lombok.RequiredArgsConstructor;
import ma.ariani.servicegestionutilisateurs.model.Groupe;
import ma.ariani.servicegestionutilisateurs.repository.GroupeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupeService {

    private final GroupeRepository groupeRepository;

    public List<Groupe> getAllGroupes() {
        return groupeRepository.findAll();
    }

    public Optional<Groupe> getGroupeById(Long id) {
        return groupeRepository.findById(id);
    }

    public Groupe saveGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    public void deleteGroupe(Long id) {
        groupeRepository.deleteById(id);
    }
} 