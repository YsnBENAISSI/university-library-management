package com.projetfinal.servicegestionutilisateurs.repository;

import ma.ariani.servicegestionutilisateurs.model.Groupe;
import ma.ariani.servicegestionutilisateurs.model.Membre;
import ma.ariani.servicegestionutilisateurs.model.Usager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {
    List<Membre> findByUsager(Usager usager);
    List<Membre> findByGroupe(Groupe groupe);
} 