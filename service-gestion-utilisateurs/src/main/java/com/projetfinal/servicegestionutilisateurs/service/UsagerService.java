package com.projetfinal.servicegestionutilisateurs.service;

import lombok.RequiredArgsConstructor;
import ma.ariani.servicegestionutilisateurs.model.Usager;
import ma.ariani.servicegestionutilisateurs.repository.UsagerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsagerService {

    private final UsagerRepository usagerRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usager> getAllUsagers() {
        return usagerRepository.findAll();
    }

    public Optional<Usager> getUsagerById(Long id) {
        return usagerRepository.findById(id);
    }

    public Optional<Usager> getUsagerByLogin(String login) {
        return usagerRepository.findByLogin(login);
    }

    public Usager saveUsager(Usager usager) {
        // Encoder le mot de passe avant de sauvegarder
        usager.setMotDePasse(passwordEncoder.encode(usager.getMotDePasse()));
        return usagerRepository.save(usager);
    }

    public void deleteUsager(Long id) {
        usagerRepository.deleteById(id);
    }

    public boolean authenticate(String login, String motDePasse) {
        Optional<Usager> usagerOpt = usagerRepository.findByLogin(login);
        
        if (usagerOpt.isPresent()) {
            Usager usager = usagerOpt.get();
            return passwordEncoder.matches(motDePasse, usager.getMotDePasse());
        }
        
        return false;
    }
} 