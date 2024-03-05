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
    private Long Id;
    //@Column(nullable = false)
    private String Nom ;
    //@Column(nullable = false)
    private String Prenom;
    //@Column(nullable = false,unique = true)
    private String email;
    //@Column(nullable = false)
    private int Numero ;
    //@Column(nullable = false)
    private String password ;

    @ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinTable(
            name="user_roles",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    private Set<Role> roles;


}
