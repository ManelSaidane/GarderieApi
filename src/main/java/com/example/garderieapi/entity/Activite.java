package com.example.garderieapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String date;
    private String heure;
    private Long responsableId;

    @ManyToOne
    @JoinColumn(name = "responsable_idd") // Nom de la colonne dans la table Activite faisant référence à l'identifiant du responsable
    private User responsable;
}
