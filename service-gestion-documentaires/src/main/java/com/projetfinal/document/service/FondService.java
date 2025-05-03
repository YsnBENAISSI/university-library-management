package com.projetfinal.document.service;

import com.ariani.servicegestionfondsdocumentaires.model.Catalogue;
import com.ariani.servicegestionfondsdocumentaires.model.Fond;
import com.ariani.servicegestionfondsdocumentaires.repository.FondRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FondService {

    private final FondRepository fondRepository;

    public List<Fond> getAllFonds() {
        return fondRepository.findAll();
    }

    public Optional<Fond> getFondById(Long id) {
        return fondRepository.findById(id);
    }

    public Optional<Fond> getFondByOuvrage(Catalogue ouvrage) {
        return fondRepository.findByOuvrage(ouvrage);
    }

    public Fond saveFond(Fond fond) {
        return fondRepository.save(fond);
    }

    public void deleteFond(Long id) {
        fondRepository.deleteById(id);
    }

    public boolean isOuvrageDisponible(Catalogue ouvrage) {
        Optional<Fond> fondOpt = fondRepository.findByOuvrage(ouvrage);
        return fondOpt.filter(fond -> fond.getNombreExemplaires() > 0).isPresent();
    }

    public boolean decrementerExemplaires(Catalogue ouvrage) {
        Optional<Fond> fondOpt = fondRepository.findByOuvrage(ouvrage);
        
        if (fondOpt.isPresent()) {
            Fond fond = fondOpt.get();
            if (fond.getNombreExemplaires() > 0) {
                fond.setNombreExemplaires(fond.getNombreExemplaires() - 1);
                fondRepository.save(fond);
                return true;
            }
        }
        
        return false;
    }

    public void incrementerExemplaires(Catalogue ouvrage) {
        Optional<Fond> fondOpt = fondRepository.findByOuvrage(ouvrage);
        
        if (fondOpt.isPresent()) {
            Fond fond = fondOpt.get();
            fond.setNombreExemplaires(fond.getNombreExemplaires() + 1);
            fondRepository.save(fond);
        }
    }
} 