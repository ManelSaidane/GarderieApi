package com.example.garderieapi.Controller;

import com.example.garderieapi.Repository.EmploiRepository;
import com.example.garderieapi.Service.EmploiService;
import com.example.garderieapi.dto.EmploiDto;
import com.example.garderieapi.entity.Emploi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1")
public class EmploiController {

    private final EmploiService emploiService;
    private final EmploiRepository emploiRepository;
    public EmploiController(EmploiService emploiService, EmploiRepository emploiRepository) {
        this.emploiService = emploiService;
        this.emploiRepository = emploiRepository;
    }

    @PostMapping("/Garderie/Groupe/{idGroupe}/AddEmploi")
    public ResponseEntity<String> AddEmploi(@PathVariable Long idGroupe ,@RequestBody MultipartFile emploi) {

        return emploiService.CreateEmploi(emploi,idGroupe);
    }
    @GetMapping("/Emplois")
    public ResponseEntity<List<Emploi>> getAllEmplois() {
        List<Emploi> emplois = emploiRepository.findAll();
        // Récupérer l'URL de base où les fichiers sont stockés sur le serveur
        String baseUrl = "http://localhost:8080/files/Emploi/";
        // Parcourir les emplois et mettre à jour les noms de fichiers par les URL complètes
        emplois.forEach(emploi -> emploi.setFileName(baseUrl + emploi.getFileName()));
        return new ResponseEntity<>(emplois, HttpStatus.OK);
    }


    @GetMapping("/Garderie/Groupe/{idGroupe}/Emploi")
    public ResponseEntity<Emploi> getEmploiForGarderie(@PathVariable Long idGroupe) {
        Emploi emploi = emploiService.getEmploiByGroupeForGarderie(idGroupe);
        if (emploi == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(emploi, HttpStatus.OK);
    }


    @GetMapping("/Responsable/Groupe/{idGroupe}/Emploi")
    public ResponseEntity<Emploi> getEmploiForResponsable(@PathVariable Long idGroupe) {
        if (emploiService.getEmploiByGroupeForResponsable(idGroupe)==null)
            return new ResponseEntity<>(emploiService.getEmploiByGroupeForResponsable(idGroupe), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(emploiService.getEmploiByGroupeForResponsable(idGroupe), HttpStatus.OK);
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
