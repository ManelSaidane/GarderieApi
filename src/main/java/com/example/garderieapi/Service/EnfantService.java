package com.example.garderieapi.Service;


import com.example.garderieapi.Repository.EnfantRepository;
import com.example.garderieapi.Repository.GroupeRepository;
import com.example.garderieapi.entity.Enfant;
import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.Groupe;
import com.example.garderieapi.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EnfantService implements IEnfantService {


    private final EnfantRepository enfantRepository;

    private final GroupeService groupeService;

    private final ParentService parentService;

    private final GarderieService garderieService;

    private final GroupeRepository groupeRepository;

    private final ResponsableService responsableService;

    private final UserService userService;

    public EnfantService(EnfantRepository enfantRepository, GroupeService groupeService, ParentService parentService, GarderieService garderieService, GroupeRepository groupeRepository, ResponsableService responsableService, UserService userService) {
        this.enfantRepository = enfantRepository;
        this.groupeService = groupeService;
        this.parentService = parentService;
        this.garderieService = garderieService;
        this.groupeRepository = groupeRepository;
        this.responsableService = responsableService;
        this.userService = userService;
    }


    //------------------------ Create Enfant ----------------------------------
    @Override
    public Enfant CreateEnfant(Long idParent,String nom, String prenom, String niveau) {
        if (nom.isEmpty() || prenom.isEmpty()||niveau.isEmpty())
            return null;
        Enfant enfant= new Enfant();
        enfant.setNom(nom);
        enfant.setPrenom(prenom);
        enfant.setNiveau(niveau);
        enfant.setGarderie(garderieService.GarderieConnectee());
        Enfant enfantSave= enfantRepository.save(enfant);
        parentService.addEnfantToParent(idParent,enfant);
        return enfantSave;
    }

    //------------------------ Update Enfant ----------------------------------
    @Override
    public String UpdateEnfant(long enfantId, String nom, String prenom, String niveau, Long idGroupe) {
        Optional<Enfant> existingEnfant = enfantRepository.findById(enfantId);
        if (existingEnfant.isPresent()) {
            Enfant EnfantToUpdate = existingEnfant.get();

            if(!nom.isEmpty())     EnfantToUpdate.setNom(nom);
            if(!prenom.isEmpty())  EnfantToUpdate.setPrenom(prenom);
            if(!niveau.isEmpty())  EnfantToUpdate.setNiveau(niveau);

            Groupe groupe= groupeService.getGroupeById(idGroupe);
            if (groupe!=null){
            EnfantToUpdate.setGroupe(groupe);
            }

            enfantRepository.save(EnfantToUpdate);
            return "L'enfant a été modifié";
        }
        return "Enfant introuvable";
    }


    //------------------------ Ajouter a groupe Enfant ----------------------------------
    @Override
    public String AjouterEnfantAGroupe(long enfantId, long groupeId) {
        Optional<Enfant> enfant = enfantRepository.findById(enfantId);

        if (enfant.isPresent()) {
            Groupe groupe = groupeService.getGroupeById(groupeId);
            Enfant existEnfant = enfant.get();
            existEnfant.setGroupe(groupe);
            enfantRepository.save(existEnfant);
            return "L'enfant ajouter avec succès";
        } else
            return "! Échec de l'ajout l'enfant";

    }

    //------------------------ Get Enfants By Garderie ----------------------------------
    @Override
    public List<Enfant> GetEnfantsByGarderie() {
        Garderie garderie=garderieService.GarderieConnectee();
        if (garderie==null) throw new IllegalArgumentException("! Échec: il ya problème d'autorisation");
        return enfantRepository.findByGarderie(garderie);
    }


    //------------------------ Get Enfants ----------------------------------

    //select avec : enfant par non et prenom et garderie   si nom et prenom ne pas vide
    //              OU enfant par non et garderie          si nom ne pas vide et prenom vide
    //              OU enfant par prenom et garderie       si nom et prenom ne pas vide
    //              OU enfant par garderie                 si nom et prenom vide
    @Override
    public List<Enfant> GetEnfantsByNomAndPrenomAndGarderie(String nom, String prenom) {

        List<Enfant> enfants ;
        Garderie gard= garderieService.GarderieConnectee();
            if (!nom.isEmpty())
        {
            if (!prenom.isEmpty()) {
                enfants=enfantRepository.findByNomAndPrenomAndGarderie(nom,prenom,gard);
            }else {
                enfants=enfantRepository.findByNomAndGarderie(nom,gard);
            }
        } else if (!prenom.isEmpty())
        {
            enfants=enfantRepository.findByPrenomAndGarderie(prenom,gard);
        } else {
                enfants= enfantRepository.findByGarderie(gard);
            }
        return enfants;
    }

    //------------------------ Get Enfants Niveau And Garderie ----------------------------------

    @Override
    public List<Enfant> GetEnfantsByNiveauAndGarderie(String niveau) {

        if (niveau.isEmpty()) throw new IllegalArgumentException("! Échec: le niveau vide.");

        Garderie gard= garderieService.GarderieConnectee();
        if (gard==null) throw new IllegalArgumentException("! Votre garderie introuvable.");

        List<Enfant> enfants = enfantRepository.findByNiveauAndGarderie(niveau,gard);
        if (enfants.isEmpty()) throw new IllegalArgumentException("! Échec: Vérifie le niveau.");

        return enfants;
    }


    //------------------------ Get Enfants By ByParent ----------------------------------
    @Override
    public Set<Enfant> GetEnfantsByParent(String email) {
        Garderie garderie =garderieService.GarderieConnectee();

        List<User> parents=userService.getByGarderieParent(garderie);
        if (parents.isEmpty())throw new IllegalArgumentException("! accès interdit: Vous êtes n'est pas garderie");

        User getParent = userService.getByEmail(email).get();
        if (parents.isEmpty())throw new IllegalArgumentException("! Parent introuvable");

        Set<Enfant> enfants = null;
        for (User parent: parents) {
            if (parent.equals(getParent)) {
                enfants = parent.getEnfants();
            }
        }
        return enfants;
    }


    //------------------------ get Enfants By Parents ----------------------------------

    @Override
    public Set<Enfant> getEnfantsByParents(Long idParent) {
        User prent= parentService.getParentById(idParent);
        return prent.getEnfants();
    }


    //------------------------ get Enfants By Groupe ----------------------------------
    @Override
    public List<Enfant> getEnfantsByGroupe(Long groupeId) {
        Garderie garderie= garderieService.GarderieConnectee();
        User responsable = responsableService.ResponsableConnectee();
        if (garderie == null && responsable ==null)
            throw new IllegalArgumentException("! accès interdit");
        Optional<Groupe> groupe =groupeRepository.findById(groupeId);
        if (groupe.isPresent()) {
            return enfantRepository.findByGroupe(groupe.get());
        }
        return null;
    }

}

