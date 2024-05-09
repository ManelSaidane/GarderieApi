package com.example.garderieapi.Service;

import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;

import java.util.List;

public interface IGarderieService {
    String creteGarderie(String nom, String prenom,
                         String email, int numero,
                         String password, String role,
                         String nomGarderie,Boolean validation);

    Garderie getGarderieById(Long id);
    List<Garderie> getAllGarderie(int page, int size) ;
    Garderie getGarderieByNom(String nomGarderie);
    List<User> getGerants();

    Garderie GarderieConnectee();

    String deleteGarderir(Long garderieId);

    String updateGarderie(Long garderieId, String nomGarderie);

    String verificationGarderie(Long garderieId);

    String updateGarderieValidation(Long garderieId);

    //------------------------ get Garderie by verification  ----------------------------------
    List<Garderie> getGarderieByVerification(Boolean valid);
}