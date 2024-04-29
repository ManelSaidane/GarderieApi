package com.example.garderieapi.entity;

import jakarta.persistence.*;
import lombok.*;


    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Data
    @Table(name="roles")
    public class Role
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable=false, unique=true)
        private String name;

    }
