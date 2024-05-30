package com.example.garderieapi.Controller;

import com.example.garderieapi.Service.EmploiService;
import com.example.garderieapi.dto.EmploiDto;
import com.example.garderieapi.entity.Emploi;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
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
    public ResponseEntity<byte[]> getEmploiForGarderie(@PathVariable Long idGroupe) throws IOException {


        return emploiService.getEmploiByGroupeForGarderie(idGroupe);
    }

    @GetMapping("/Responsable/Groupe/{idGroupe}/Emploi")
    public ResponseEntity<byte[]> getEmploiForResponsable(@PathVariable Long idGroupe) throws IOException {
        return emploiService.getEmploiByGroupeForResponsable(idGroupe);
    }

    @GetMapping("/Parent/{enfantId}/Emploi")
    public ResponseEntity<byte[]> getEmploiForParent(@PathVariable Long enfantId) throws IOException {
        return emploiService.getEmploiByGroupeForResponsable(enfantId);
    }


    @PutMapping("/Garderie/Groupe/{idGroupe}/Emploi/{emploiId}")
    public ResponseEntity<String> updateEmploi(@PathVariable Long emploiId,@RequestBody EmploiDto emploiDto) {
        String result=emploiService.updateEmploi(emploiId,emploiDto.getFile(),emploiDto.getName());
        if (result!="Emploi mis à jour avec succès")
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}