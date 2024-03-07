package com.example.garderieapi.dto;

import com.example.garderieapi.entity.Role;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignUpDto {

    private String nom ;
    private String prenom;
    private String email;
    private int numero ;
    private String password ;
    private String role;
}