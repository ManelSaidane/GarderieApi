package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.Absence;
import com.example.garderieapi.entity.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AbsenceRepository extends JpaRepository<Absence,Long> {

    List<Absence> findByEnfant(Enfant enfant);

    Long countByEnfant(Enfant enfant);


    @Query("SELECT SUM(a.nbrHeures) FROM Absence a WHERE a.enfant.id = :enfantId")
    Long sumNbrHeuresByEnfantId(@Param("enfantId") Long enfantId);

}
