package com.example.garderieapi.Service;
import com.example.garderieapi.Repository.ActiviteRepository;
import com.example.garderieapi.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActiviteService implements  ActiviteServiceImpl {

    private final ActiviteRepository activiteRepository;
    private final ResponsableService responsableService;
    private final GarderieService garderieService;


    public ActiviteService(ActiviteRepository activiteRepository, ResponsableService responsableService, GarderieService garderieService) {
        this.activiteRepository = activiteRepository;
        this.responsableService = responsableService;
        this.garderieService = garderieService;
    }

    @Override
    public List<Activite> getAllActivites() {
        User responsable = responsableService.ResponsableConnectee();
        Garderie garderie = garderieService.GarderieConnectee();
        if (responsable!=null) {
            return activiteRepository.findByResponsable(responsable);
        }
        else if (garderie != null) {
            return activiteRepository.findByGarderie(garderie);
        }
        return null;
    }

    @Override
    public Activite getActiviteById(Long id) {
        Optional<Activite> activite = activiteRepository.findById(id);
        if(activite.isPresent()) {
            User responsable = responsableService.ResponsableConnectee();
            Garderie garderie = garderieService.GarderieConnectee();
            if (activite.get().getResponsable()==responsable
                    || activite.get().getGarderie().equals(garderie)) return activite.get();
        }
        return null;
    }

    @Override
    public String deleteActivite(Long id) {
        Optional<Activite> activite = activiteRepository.findById(id);
        if(activite.isPresent()) {
            User responsable = responsableService.ResponsableConnectee();
            Garderie garderie = garderieService.GarderieConnectee();
            if (activite.get().getResponsable()==responsable
                    || activite.get().getGarderie().equals(garderie))
            {activiteRepository.deleteById(id);
                return "l'activité supprimer avec succès";
            }
        }
        return "échec de supprimer l'activité (l'activité introuvable)";
    }

    @Override
    public String createActivite(String nom, String date, String heure) {
        if (nom.isEmpty() || date.isEmpty()||heure.isEmpty())
            return null;
        Garderie garderie = null;
        User responsable = responsableService.ResponsableConnectee();

        if( responsable != null){
            garderie = responsable.getGarderieRespo();
        }else garderie = garderieService.GarderieConnectee();

        if (garderie != null) {
            Activite activite = new Activite();
            activite.setNom(nom);
            activite.setDate(date);
            activite.setHeure(heure);
            activite.setResponsable(responsable);
            activite.setGarderie(garderie);
            activiteRepository.save(activite);
            return "Activité créée avec succès";
        }
        return "! Échec: garderie introuvable";
    }

    @Override
    public String updateActivite(Long id, String nom, String date, String heure) {


        Optional<Activite> activite = activiteRepository.findById(id);
        if (activite.isPresent()) {

            Garderie garderie=garderieService.GarderieConnectee();
            if (garderie!= activite.get().getGarderie()) return ("! Échec: accès interdit");

            if (!nom.isEmpty()) activite.get().setNom(nom);

            if (!date.isEmpty()) activite.get().setDate(date);

            if (!heure.isEmpty()) activite.get().setHeure(heure);
            activiteRepository.save(activite.get());
            return "Activité mise à jour avec succès";
        }
        return "Activité non trouvée";
    }

}