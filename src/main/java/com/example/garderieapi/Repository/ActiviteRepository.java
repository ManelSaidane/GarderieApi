package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.Activite;
import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActiviteRepository extends JpaRepository<Activite,Long> {

    List<Activite> findByResponsable(User responsable);
    List<Activite> findByGarderie(Garderie garderie);

}