package com.example.garderieapi.entity;

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
    @Column(name = "nom")
    private String nom ;
    //@Column(nullable = false)
    private String prenom;
    //@Column(nullable = false,unique = true)
    private String email;
    //@Column(nullable = false)
    private int numero ;
    //@Column(nullable = false)
    private String password ;

    @ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinTable(
            name="user_roles",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    private Set<Role> roles;


}
