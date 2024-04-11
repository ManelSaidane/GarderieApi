package com.example.garderieapi.Controller;

import com.example.garderieapi.Service.AbsenceService;
import com.example.garderieapi.dto.AbsenceDto;
import com.example.garderieapi.entity.Absence;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class AbsenceController {
    private final AbsenceService absenceService;

    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @PostMapping({"/Garderie/Absence/Add","/Responsable/Absence/Add"})
    public ResponseEntity<String> CreateAbsenceGarderie(@RequestBody AbsenceDto absenceDto){
        return absenceService.createAbsence(absenceDto.getNbrHeures(),absenceDto.getEnfantId());
    }
    @PatchMapping({"/Garderie/Absence/{absId}/Update","/Responsable/Absence/{absId}/Update"})
    public ResponseEntity<String> UpdateAbsence(@PathVariable Long absId, int nbrHeures){
        return absenceService.updateAbsence(absId,nbrHeures);
    }

    @DeleteMapping({"/Garderie/Absence/{absId}","/Responsable/Absence/{absId}"})
    public ResponseEntity<String> DeleteAbsence(@PathVariable Long absId){
        return absenceService.deleteAbsence(absId);
    }

    @GetMapping({"/Garderie/Absence/getByEnfant/{enfantId}",
            "/Responsable/Absence/getByEnfant/{enfantId}",
            "/Parent/Absence/getByEnfant/{enfantId}"})
    public ResponseEntity<List<Absence>> getByEnfant(@PathVariable Long enfantId){
        return absenceService.getAbsencesByEnfantId(enfantId);
    }

    @GetMapping({"/Garderie/Absence/TotalAbsencesEnfant/{enfantId}",
            "/Responsable/Absence/TotalAbsencesEnfant/{enfantId}",
            "/Parent/Absence/TotalAbsencesEnfant/{enfantId}"})
    public ResponseEntity<Long> getSommeHeuresAbsencePourEnfant(@PathVariable Long enfantId){
        return absenceService.getSommeHeuresAbsencePourEnfant(enfantId);
    }


    @GetMapping({"/Garderie/Absence/NombreAbsences/{enfantId}",
            "/Responsable/Absence/NombreAbsences/{enfantId}",
            "/Parent/Absence/NombreAbsences/{enfantId}"})
    public ResponseEntity<Long> getNombreAbsencesPourEnfant(@PathVariable Long enfantId) {
        return absenceService.getNombreAbsencesPourEnfant(enfantId);
    }

    }
