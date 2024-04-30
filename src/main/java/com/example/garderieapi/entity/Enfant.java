package com.example.garderieapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name ="Enfants")
public class Enfant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String niveau;

    @ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    private Groupe groupe;

    @ManyToOne (fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    private Garderie garderie;
}
