package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.ActiviteRepository;
import com.example.garderieapi.dto.ActiviteDto;
import com.example.garderieapi.entity.Activite;
import com.example.garderieapi.entity.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActiviteService implements  ActiviteServiceImpl {

    private final ActiviteRepository activiteRepository;


    public ActiviteService(ActiviteRepository activiteRepository) {
        this.activiteRepository = activiteRepository;
    }

    @Override
    public List<ActiviteDto> getAllActivites() {
        return activiteRepository.findAll().stream()
                .map(this::mapActiviteToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ActiviteDto getActiviteById(Long id) {
        Activite activite = activiteRepository.findById(id).orElse(null);
        return activite != null ? mapActiviteToDto(activite) : null;
    }

    @Override
    public String createActivite(ActiviteDto activiteDto) {
        Activite activite = mapDtoToActivite(activiteDto);
        activiteRepository.save(activite);
        return "Activité créée avec succès";
    }

    @Override
    public String updateActivite(Long id, ActiviteDto activiteDto) {
        Activite existingActivite = activiteRepository.findById(id).orElse(null);
        if (existingActivite != null) {
            Activite updatedActivite = mapDtoToActivite(activiteDto);
            updatedActivite.setId(id);
            activiteRepository.save(updatedActivite);
            return "Activité mise à jour avec succès";
        }
        return "Activité non trouvée";
    }

 /*   public List<Activite> getActivitesByGarderieId(Long garderieId) {
        return activiteRepository.findByGarderieId(garderieId);
    }
*/

    @Override
    public String deleteActivite(Long id) {
        if (activiteRepository.existsById(id)) {
            activiteRepository.deleteById(id);
            return "Activité supprimée avec succès";
        }
        return "Activité non trouvée";
    }



    private ActiviteDto mapActiviteToDto(Activite activite) {
        ActiviteDto activiteDto = new ActiviteDto();
        activiteDto.setId(activite.getId());
        activiteDto.setNom(activite.getNom());
        activiteDto.setDate(activite.getDate());
        activiteDto.setHeure(activite.getHeure());
        return activiteDto;
    }

    private Activite mapDtoToActivite(ActiviteDto activiteDto) {
        Activite activite = new Activite();
        activite.setNom(activiteDto.getNom());
        activite.setDate(activiteDto.getDate());
        activite.setHeure(activiteDto.getHeure());
        return activite;
    }
}