package com.example.garderieapi.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name ="Absences")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private int nbrHeures ;

    @ManyToOne
    private Enfant enfant;

    @ManyToOne
    private User responsable;
}
