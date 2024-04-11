package com.example.garderieapi.Controller;

import com.example.garderieapi.Service.EmploiService;
import com.example.garderieapi.dto.EmploiDto;
import com.example.garderieapi.entity.Emploi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1")
public class EmploiController {

    private final EmploiService emploiService;

    public EmploiController(EmploiService emploiService) {
        this.emploiService = emploiService;
    }

    @PostMapping("/Garderie/Groupe/{idGroupe}/AddEmploi")
    public ResponseEntity<String> AddEmploi(@PathVariable Long idGroupe ,@RequestBody MultipartFile emploi) {

        return emploiService.CreateEmploi(emploi,idGroupe);
    }

    @GetMapping("/Garderie/Groupe/{idGroupe}/Emploi")
    public ResponseEntity<Emploi> getEmploiForGarderie(@PathVariable Long idGroupe) {
        if (emploiService.getEmploiByGroupeForGarderie(idGroupe)==null)
            return new ResponseEntity<>(emploiService.getEmploiByGroupeForGarderie(idGroupe), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(emploiService.getEmploiByGroupeForGarderie(idGroupe), HttpStatus.FOUND);
    }

    @GetMapping("/Responsable/Groupe/{idGroupe}/Emploi")
    public ResponseEntity<Emploi> getEmploiForResponsable(@PathVariable Long idGroupe) {
        if (emploiService.getEmploiByGroupeForResponsable(idGroupe)==null)
            return new ResponseEntity<>(emploiService.getEmploiByGroupeForResponsable(idGroupe), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(emploiService.getEmploiByGroupeForResponsable(idGroupe), HttpStatus.FOUND);
    }

    @GetMapping("/Parent/{enfantId}/Emploi")
    public ResponseEntity<Emploi> getEmploiForParent(@PathVariable Long enfantId) {
        if (emploiService.getEmploiByEnfantForParent(enfantId)==null)
            return new ResponseEntity<>(emploiService.getEmploiByEnfantForParent(enfantId), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(emploiService.getEmploiByEnfantForParent(enfantId), HttpStatus.FOUND);
    }


    @PutMapping("/Garderie/Groupe/{idGroupe}/Emploi/{emploiId}")
    public ResponseEntity<String> updateEmploi(@PathVariable Long emploiId,@RequestBody EmploiDto emploiDto) {
        String result=emploiService.updateEmploi(emploiId,emploiDto.getFile(),emploiDto.getName());
        if (result!="Emploi mis à jour avec succès")
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
