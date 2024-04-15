package com.example.garderieapi.Service;

import com.example.garderieapi.entity.Enfant;
import com.example.garderieapi.entity.User;

import java.util.List;

public interface IParentService {
    String createParent(String nom, String prenom,
                        String email, int numero,
                        String password, String role,
                        String enfantNom, String enfantPrenom, String enfantNiveau);

    List<User> getParentByGarderie();

    User getParentById(Long idParent);

    User ParentConnectee();

    String updateParent(Long idParent, String nom, String prenom, String email, int numero, String password);

    void addEnfantToParent(Long idParent, Enfant enfant);

    User parentConnectee();

    String deleteParent (Long idParent);

}
