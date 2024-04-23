package com.example.garderieapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ActiviteDto {

    private Long id;
    private String nom;
    private String date;
    private String heure;
    private Long responsableId;

}
