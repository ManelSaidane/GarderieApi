package com.example.garderieapi.Service;



import com.example.garderieapi.entity.Activite;

import java.util.List;

public interface ActiviteServiceImpl  {

        List<Activite> getAllActivites();

        Activite getActiviteById(Long id);

        String deleteActivite(Long id);

        String createActivite(String nom, String date, String heure);


        String updateActivite(Long id, String nom, String date, String heure);


}
