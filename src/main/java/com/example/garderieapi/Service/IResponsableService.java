package com.example.garderieapi.Service;

import com.example.garderieapi.entity.User;

import java.util.List;

public interface IResponsableService {
    String createResponsable(String nom, String prenom,
                             String email, int numero,
                             String password, String role);

    List<User> getAllResponsableByGarderie();

    String updateResponsable(Long id, String nom, String prenom, String email, String password, int numero);

    User ResponsableConnectee();

    User getResponsableById(Long id);

    String deleteResponsable(Long id);
}
