package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupeRepository extends JpaRepository<Groupe,Long> {
    List<Groupe> findByGarderie(Garderie garderie);
    Optional<Groupe> findById(Long id);
    Optional<Groupe>findByIdAndGarderie(Long id,Garderie garderie);
    List<Groupe>findByResponsablesId(Long responsables_id);
    void deleteByResponsablesId(Long responsables_id);
}
