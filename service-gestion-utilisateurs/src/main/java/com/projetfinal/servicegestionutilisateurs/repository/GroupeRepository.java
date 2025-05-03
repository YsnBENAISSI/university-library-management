package com.projetfinal.servicegestionutilisateurs.repository;

import ma.ariani.servicegestionutilisateurs.model.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {
} 