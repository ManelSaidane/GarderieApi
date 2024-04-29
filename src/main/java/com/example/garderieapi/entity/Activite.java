package com.example.garderieapi.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "Activites")
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String date;
    private String heure;


    @ManyToOne
    private User responsable;

    @ManyToOne
    private Garderie garderie;
}
