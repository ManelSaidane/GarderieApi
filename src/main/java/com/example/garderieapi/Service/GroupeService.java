package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.EnfantRepository;
import com.example.garderieapi.Repository.GroupeRepository;
import com.example.garderieapi.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupeService implements IgroupeService {

    private final GroupeRepository groupeRepository;
    private final GarderieService garderieService;
    private final EnfantRepository enfantRepository;
    private final UserService userService;
    private final ResponsableService responsableService;


    @Autowired
    public GroupeService(GroupeRepository groupeRepository, GarderieService garderieService, EnfantRepository enfantRepository, UserService userService, ResponsableService responsableService) {
        this.groupeRepository = groupeRepository;
        this.garderieService = garderieService;
        this.enfantRepository = enfantRepository;
        this.userService = userService;
        this.responsableService = responsableService;

    }

    //------------------------ Create Groupe----------------------------------
    @Override
    public Groupe createGroupe(String nom,String salle,String niveau, Long IdResponsable) {
        Garderie garderie= garderieService.GarderieConnectee();
        if (garderie ==null) throw new IllegalArgumentException("! accès interdit: Vous êtes n'est pas garderie");
        Groupe groupe = new Groupe();
        groupe.setNom(nom);
        groupe.setSalle(salle);
        groupe.setNiveau(niveau);
        groupe.setGarderie(garderie);
        groupe.setResponsables(userService.getUserById(IdResponsable).get());
        return groupeRepository.save(groupe);
    }


    //------------------------ get Groupe By Garderie ----------------------------------
    @Override
    public List<Groupe> getGroupeByGarderie() {
        Garderie garderie=garderieService.GarderieConnectee();
        if (garderie == null) throw new IllegalArgumentException("! accès interdit: Vous êtes n'est pas garderie");
        return groupeRepository.findByGarderie(garderie);
    }
    //------------------------ get Groupe By responsable for gard ----------------------------------
    @Override
    public List<Groupe> getGroupeByResponsable(Long responsableId) {
        User responsable= responsableService.getResponsableById(responsableId);
        if (responsable == null) return null;

        return groupeRepository.findByResponsablesId(responsable.getId());
    }


    //------------------------ get Groupe By responsable for responsable ----------------------------------
    @Override
    public List<Groupe> getGroupeByResponsable() {
        User responsable= responsableService.ResponsableConnectee();
        if (responsable == null) return null;

        return groupeRepository.findByResponsablesId(responsable.getId());
    }
    //
    //------------------------ get Groupe By enfantId ----------------------------------
    @Override
    public Groupe getGroupeForEnfant(Long enfantId) {
        Optional<Enfant> optionalEnfant = enfantRepository.findById(enfantId);
        if (optionalEnfant.isPresent()) {
            Enfant enfant = optionalEnfant.get();
            return enfant.getGroupe();
        }
        return null;
    }


    //------------------------ get Groupe By Id ----------------------------------
    @Override
    public Groupe getGroupeById(Long idGroupe) {
        Garderie garderie =garderieService.GarderieConnectee();
        User responsable = responsableService.ResponsableConnectee();
        if (garderie == null && responsable==null) throw new IllegalArgumentException("! Échec: il ya problème d'autorisation");
        Optional<Groupe> groupe=groupeRepository.findById(idGroupe);
        if (groupe.isPresent()){

            if (responsable!=null && responsable.getGarderieRespo()!= groupe.get().getGarderie())
                throw new IllegalArgumentException("! Échec: il ya problème d'autorisation");

            if (!groupe.get().getGarderie().equals(garderie)){
                throw new IllegalArgumentException("! Échec: il ya problème d'autorisation");
            }
            return groupe.get();
        }
        return null;
    }


    //------------------------ updateGroupe----------------------------------

    @Override
    public Groupe updateGroupe2(Long idGroupe, String nom,String salle ,String niveau) {
        Optional<Groupe> existingGroupe = groupeRepository.findById(idGroupe);
        Garderie garderie=garderieService.GarderieConnectee();
        if (existingGroupe.isPresent()) {

        /*   if (!existingGroupe.get().getGarderie().equals(garderie))
                throw new IllegalArgumentException("! Accès interdit: cette groupe appartient une autre garderie");*/

            Groupe groupeToUpdate = existingGroupe.get();

        /*    if (nom.isEmpty())    nom=existingGroupe.get().getNom();
            if (salle.isEmpty())  salle=existingGroupe.get().getSalle();
            if (niveau.isEmpty()) niveau=existingGroupe.get().getNiveau();*/



            groupeToUpdate.setNom(nom);
            groupeToUpdate.setSalle(salle);
            groupeToUpdate.setNiveau(niveau);


            return groupeRepository.save(groupeToUpdate);
        } else {
            throw new IllegalArgumentException("! Groupe avec l'ID " + idGroupe + " introuvable.");
        }
    }
    @Override
    public Groupe updateGroupe(Long idGroupe, String nom,String salle ,String niveau,Long idRespondable) {
        Optional<Groupe> existingGroupe = groupeRepository.findById(idGroupe);
        Garderie garderie=garderieService.GarderieConnectee();
         if (existingGroupe.isPresent()) {

            if (!existingGroupe.get().getGarderie().equals(garderie))
                throw new IllegalArgumentException("! Accès interdit: cette groupe appartient une autre garderie");

            Groupe groupeToUpdate = existingGroupe.get();

            if (nom.isEmpty())    nom=existingGroupe.get().getNom();
            if (salle.isEmpty())  salle=existingGroupe.get().getSalle();
            if (niveau.isEmpty()) niveau=existingGroupe.get().getNiveau();



            groupeToUpdate.setNom(nom);
            groupeToUpdate.setSalle(salle);
            groupeToUpdate.setNiveau(niveau);


            return groupeRepository.save(groupeToUpdate);
        } else {
            throw new IllegalArgumentException("! Groupe avec l'ID " + idGroupe + " introuvable.");
        }
    }

    //------------------------ Supprimer Groupe ----------------------------------
    @Override
    public String deleteGroupe(Long id) {
        List<Groupe> groupes=getGroupeByGarderie();
        Groupe groupe = getGroupeById(id);
        if(! groupes.contains(groupe)) return "! Échec: le groupe introuvable.";
      //  groupe.getResponsables().clear();
        groupeRepository.deleteById(id);
        return "Le groupe a été supprimé";
    }


    //------------------------ add Responsable To Groupe ----------------------------------
    @Override
    public String addResponsableToGroupe(Long groupeId, Long userId) {
        Garderie garderie=garderieService.GarderieConnectee();

        Optional<Groupe> groupe = groupeRepository.findById(groupeId);
        if (groupe.isPresent()){
            if (!groupe.get().getGarderie().equals(garderie)) return "! Groupe introuvable";
            Optional<User> responsable = userService.getUserById(userId);
            if (responsable.isPresent()){
                groupe.get().getResponsables();
                groupeRepository.save(groupe.get());
                return "Responsable a ajouté avec succès";
            }else return "Responsable introvable";
        }
        return "! Groupe introuvable";
    }
}