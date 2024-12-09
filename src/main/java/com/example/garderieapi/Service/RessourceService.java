package com.example.garderieapi.Service;


import com.example.garderieapi.Repository.RessourceRepository;
import com.example.garderieapi.entity.Ressource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RessourceService {

    private final RessourceRepository ressourceRepository;

    @Autowired
    public RessourceService(RessourceRepository ressourceRepository) {
        this.ressourceRepository = ressourceRepository;
    }

    public List<Ressource> getAllRessources() {
        return ressourceRepository.findAll();
    }

    public Ressource addRessource(Ressource ressource) {
        return ressourceRepository.save(ressource);
    }

    public void deleteRessource(Long id) {
        ressourceRepository.deleteById(id);
    }

    public Ressource updateRessource(Long id, Ressource updatedRessource) {
        Ressource existingRessource = ressourceRepository.findById(id).orElse(null);
        if (existingRessource != null) {
            existingRessource.setNbrPieces(updatedRessource.getNbrPieces());
            existingRessource.setDescription(updatedRessource.getDescription());
            existingRessource.setPrix(updatedRessource.getPrix());
            existingRessource.setFournisseurs(updatedRessource.getFournisseurs());
            return ressourceRepository.save(existingRessource);
        }
        return null;
    }
}
