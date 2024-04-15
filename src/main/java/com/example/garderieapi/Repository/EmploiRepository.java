package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.Emploi;
import com.example.garderieapi.entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmploiRepository extends JpaRepository<Emploi ,Long> {
    Optional<Emploi> findById(Long Id);
    Optional<Emploi> findByGroupe(Groupe groupe);

}
