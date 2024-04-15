package com.example.garderieapi.Service;

import com.example.garderieapi.entity.Enfant;

import java.util.List;
import java.util.Set;

public interface IEnfantService {


    Enfant CreateEnfant(Long idParent,String nom, String prenom, String niveau);

    String UpdateEnfant(long enfantId, String nom, String prenom, String niveau, Long idGroupe);

    String AjouterEnfantAGroupe(long enfantId, long groupeId);

    List<Enfant> GetEnfantsByGarderie();

    List<Enfant> GetEnfantsByNomAndPrenomAndGarderie(String nom,String prenom);

    List<Enfant> GetEnfantsByNiveauAndGarderie(String niveau);

    Set<Enfant> GetEnfantsByParent(String email);

    Set<Enfant> getEnfantsByParents(Long idParent);

    List<Enfant> getEnfantsByGroupe(Long groupeId);
}
