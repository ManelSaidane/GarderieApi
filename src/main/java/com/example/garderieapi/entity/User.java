package com.example.garderieapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name ="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom ;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false,unique = true)
    private String email;
    //@Column(nullable = false)
    private int numero ;
    @Column(nullable = false)
    private String password ;




    @ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinTable(
            name="user_roles",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    private Set<Role> roles;


//    @OneToOne (fetch = FetchType.EAGER, cascade= CascadeType.ALL)
//    @JoinTable(
//            name="gerant_Garderie",
//            joinColumns={@JoinColumn(name="gerant_id", referencedColumnName="id")},
//            inverseJoinColumns={@JoinColumn(name="garderie_id", referencedColumnName="id")})
//    private Garderie garderie;


    @ManyToOne (fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name="Garderie_Responsable",
            joinColumns={@JoinColumn(name="responsable_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="garderie_id", referencedColumnName="id")})
    private Garderie garderieRespo;


    @OneToMany (fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinTable(
            name="Parent_Enfant",
            joinColumns={@JoinColumn(name="parent_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="enfant_id", referencedColumnName="id")})
    private Set<Enfant> Enfants;

    @ManyToOne (fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name="parent_Garderie",
            joinColumns={@JoinColumn(name="parent_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="garderie_id", referencedColumnName="id")})
    private Garderie garderieParent;


}
