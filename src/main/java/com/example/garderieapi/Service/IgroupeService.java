package com.example.garderieapi.Service;

import com.example.garderieapi.entity.Groupe;

import java.util.List;

public interface IgroupeService {
    Groupe createGroupe(String nom,String salle,String niveau , Long IdResponsable);
    List<Groupe> getGroupeByGarderie();

    List<Groupe> getGroupeByResponsable(Long responsableId);

    List<Groupe> getGroupeByResponsable();

    Groupe getGroupeForEnfant(Long enfantId);

    Groupe getGroupeById(Long idGroupe);

    Groupe updateGroupe2(Long idGroupe, String nom, String salle, String niveau);

    Groupe updateGroupe(Long idGroupe, String nom, String salle, String niveau, Long idRespondable);

    String deleteGroupe(Long id);

    String addResponsableToGroupe(Long groupeId, Long userId);
}
