package com.example.garderieapi.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class GroupeDto {
    private String nom="" ;
    private String salle="" ;
    private String niveau="" ;
    private Long idResponsable;
}
