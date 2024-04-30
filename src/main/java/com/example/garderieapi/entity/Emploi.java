package com.example.garderieapi.entity;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name ="Emplois")
public class Emploi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String FileName;


    @OneToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;

//    @OneToMany
//    @JoinColumn(name = "groupe_id")
//    private Groupe groupe;
}
