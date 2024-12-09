package com.example.garderieapi.dto;

import lombok.Data;

@Data
public class UpdateResponsableDto {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private int numero;
}
