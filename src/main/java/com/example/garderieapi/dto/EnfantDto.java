package com.example.garderieapi.dto;

import lombok.Data;

@Data
public class EnfantDto {
    private Long idParent;
    private String nom;
    private String prenom;
    private String niveau;
    private Long idGroupe;
}
