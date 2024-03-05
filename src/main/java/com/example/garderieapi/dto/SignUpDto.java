package com.example.garderieapi.dto;

import com.example.garderieapi.entity.Role;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignUpDto {

    private String Nom ;
    private String Prenom;
    private String Email;
    private int Numero ;
    private String Password ;
    private String Role;
}