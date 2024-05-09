package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.FournitureRepository;
import com.example.garderieapi.entity.Fourniture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FournitureService {
    @Autowired
    private FournitureRepository fournitureRepository;

    public List<Fourniture> getAllFournitures() {
        return fournitureRepository.findAll();
    }

    public Fourniture addFourniture(Fourniture fourniture) {
        return fournitureRepository.save(fourniture);
    }

    public void removeFourniture(Long id) {
        fournitureRepository.deleteById(id);
    }

    public Fourniture updateFourniture(Long id, Fourniture fourniture) {
        Optional<Fourniture> optionalFourniture = fournitureRepository.findById(id);
        if (optionalFourniture.isPresent()) {
            Fourniture existingFourniture = optionalFourniture.get();
            existingFourniture.setNom(fourniture.getNom());
            existingFourniture.setQuantite(fourniture.getQuantite());
            existingFourniture.setEmplacement(fourniture.getEmplacement());
            // Enregistrer la fourniture mise à jour dans la base de données
            Fourniture updatedFourniture = fournitureRepository.save(existingFourniture);
            return updatedFourniture;
        } else {
            // Si la fourniture n'est pas trouvée, vous pouvez lancer une exception ou retourner null
            return null;
        }
    }

    public List<Fourniture> getFournituresParNom(String nom) {
        return fournitureRepository.findByNomContainingIgnoreCase(nom);
    }
}
