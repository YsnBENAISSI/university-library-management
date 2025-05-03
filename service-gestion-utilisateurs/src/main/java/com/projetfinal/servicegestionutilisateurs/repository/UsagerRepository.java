package com.projetfinal.servicegestionutilisateurs.repository;

import ma.ariani.servicegestionutilisateurs.model.Usager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsagerRepository extends JpaRepository<Usager, Long> {
    Optional<Usager> findByLogin(String login);
} 