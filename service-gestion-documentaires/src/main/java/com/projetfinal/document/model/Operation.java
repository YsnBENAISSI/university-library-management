package com.projetfinal.document.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long usagerId;
    
    @ManyToOne
    private Catalogue ouvrage;
    
    @Enumerated(EnumType.STRING)
    private TypeOperation type;
    
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Boolean actif;
    
    public enum TypeOperation {
        EMPRUNT, RESERVATION
    }
} 