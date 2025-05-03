package com.projetfinal.servicegestionutilisateurs.service;

import ma.ariani.servicegestionutilisateurs.model.Usager;
import ma.ariani.servicegestionutilisateurs.repository.UsagerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsagerDetailsService implements UserDetailsService {

    private final UsagerRepository usagerRepository;

    public UsagerDetailsService(UsagerRepository usagerRepository) {
        this.usagerRepository = usagerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usager usager = usagerRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usager non trouvé avec le login: " + username));

        return new User(usager.getLogin(), usager.getMotDePasse(), new ArrayList<>());
    }
} 