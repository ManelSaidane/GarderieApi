package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.Enfant;
import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnfantRepository extends JpaRepository<Enfant,Long> {

    List<Enfant> findByGroupe(Groupe groupe);
    List<Enfant> findByNomAndPrenomAndGarderie(String nom,String prenom,Garderie garderie);
    List<Enfant> findByNomAndGarderie(String nom,Garderie garderie);
    List<Enfant> findByPrenomAndGarderie(String Pprenom,Garderie garderie);
    List<Enfant> findByGarderie(Garderie garderie);
    List<Enfant> findByNiveauAndGarderie(String niveau,Garderie garderie);

}
