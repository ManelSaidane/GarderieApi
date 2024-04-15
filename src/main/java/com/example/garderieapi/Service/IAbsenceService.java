package com.example.garderieapi.Service;

import com.example.garderieapi.entity.Absence;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAbsenceService {
    ResponseEntity<String> createAbsence(int nbrHeures , Long enfantId);
    ResponseEntity<String> updateAbsence(Long idAbsence, int nbrHeures) ;
    ResponseEntity<String> deleteAbsence(Long idAbsence);
    ResponseEntity<List<Absence>> getAbsencesByEnfantId(Long enfantId);
    ResponseEntity<Long> getNombreAbsencesPourEnfant(Long enfantId);
    ResponseEntity<Long> getSommeHeuresAbsencePourEnfant(Long enfantId);






}
