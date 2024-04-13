package com.example.garderieapi.Service;

import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;

import java.util.List;

public interface IGarderieService {
    String creteGarderie(String nom, String prenom,
                         String email, int numero,
                         String password, String role,
                         String nomGarderie);

    Garderie getGarderieById(Long id);
    List<Garderie> getAllGarderie();
    Garderie getGarderieByNom(String nomGarderie);
    List<User> getGerants();

    Garderie GarderieConnectee();

    String deleteGarderir(Long garderieId);

    String updateGarderie(Long garderieId, String nomGarderie);
}
