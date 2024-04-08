package com.example.garderieapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Groupes")
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom ;

    @Column(nullable = false)
    private String salle ;

    @Column(nullable = false)
    private String niveau ;


    @ManyToOne (fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinTable(
            name="Groupe_Garderie",
            joinColumns={@JoinColumn(name="groupe_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="garderie_id", referencedColumnName="id")})
    private Garderie garderie;

    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinTable(
            name="groupe_responsable",
            joinColumns={@JoinColumn(name="groupe_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="id")})
    private List<User> responsables;

}
