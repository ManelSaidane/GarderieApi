package com.example.garderieapi.Controller;

import com.example.garderieapi.Service.EnfantService;
import com.example.garderieapi.dto.EnfantDto;
import com.example.garderieapi.entity.Enfant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1")
public class EnfantController {
    private final EnfantService enfantService;

    public EnfantController(EnfantService enfantService) {
        this.enfantService = enfantService;
    }

    @PostMapping("/Garderie/Enfant/Create")
    public ResponseEntity<String> CreateEnfant(@RequestBody EnfantDto enfantDto){

        Enfant enfant=enfantService.CreateEnfant(enfantDto.getIdParent(),enfantDto.getNom(),enfantDto.getPrenom(),
                enfantDto.getNiveau());
        if (enfant==null) return new ResponseEntity<>("! Echec: il ya un paramètre vide.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Enfant créé.", HttpStatus.CREATED);
    }

    @GetMapping("/Garderie/Enfant/GetAll")
    public List<Enfant> GetAllEnfant(){
        List<Enfant> enfants=enfantService.GetEnfantsByGarderie();
        if (enfants.isEmpty())return new ResponseEntity<>(enfants, HttpStatus.NOT_FOUND).getBody();
        return new ResponseEntity<>(enfants, HttpStatus.FOUND).getBody();
    }

    @PutMapping("/Garderie/Enfant/{idEnfant}/Update")
    public ResponseEntity<String> UpdateEnfant(@PathVariable Long idEnfant,@RequestBody EnfantDto enfantDto){

        String result= enfantService.UpdateEnfant(idEnfant,enfantDto.getNom(),enfantDto.getPrenom(),
                enfantDto.getNiveau(),enfantDto.getIdGroupe());
        if (result.equals("L'enfant a été modifié"))
            return new ResponseEntity<>(result,HttpStatus.OK);

        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);

    }

    @GetMapping({"/Garderie/Enfant/Parent/{idParent}","Parent/{idParent}/Enfant"})
    public ResponseEntity<Set<Enfant>> GetEnfants(@PathVariable Long idParent ){
                        return new ResponseEntity<>(enfantService.getEnfantsByParents(idParent), HttpStatus.OK);
    }

    @GetMapping("/Garderie/Enfant/Niveau/{niveau}")
    public ResponseEntity<List<Enfant>> getEnfantsByNiveau(@PathVariable String niveau) {
        List<Enfant> enfants = enfantService.GetEnfantsByNiveauAndGarderie(niveau);
        if (enfants.isEmpty())
            return new ResponseEntity<>(enfants, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(enfants, HttpStatus.FOUND);
    }
    @GetMapping("/Garderie/Enfant/Search/NomEtPrenom")
    public ResponseEntity<List<Enfant>> searchEnfants(@RequestBody String nomEnfant  ,@RequestBody String prenomEnfant ){
        List<Enfant> enfants= enfantService.GetEnfantsByNomAndPrenomAndGarderie(nomEnfant,prenomEnfant);
        if (enfants.isEmpty())
            return new ResponseEntity<>(enfants,HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(enfants,HttpStatus.FOUND);
    }

    @GetMapping("/Garderie/Enfant/Search/ParentEmail")
    public ResponseEntity<Set<Enfant>> searchEnfants(@RequestBody String parentEmail ){
        Set<Enfant> enfants= enfantService.GetEnfantsByParent(parentEmail);
        if (enfants.isEmpty())
            return new ResponseEntity<>(enfants,HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(enfants,HttpStatus.FOUND);
    }


    @GetMapping({"/Garderie/Groupe/{groupeId}/Enfant/","/Responsable/Groupe/{groupeId}/Enfant/"})
    public ResponseEntity<List<Enfant>> searchEnfants(@PathVariable Long groupeId ){
        List<Enfant> enfants= enfantService.getEnfantsByGroupe(groupeId);
        if (enfants.isEmpty())
            return new ResponseEntity<>(enfants,HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(enfants,HttpStatus.FOUND);
    }


    @PatchMapping("/Garderie/Enfant/{enfantId}/toGroupe")
    public ResponseEntity<String>AjouterEnfantAGroupe(@PathVariable Long enfantId, @RequestBody Long groupeId)
    {
        String result=enfantService.AjouterEnfantAGroupe(enfantId,groupeId);
        if (result.equals("L'enfant ajouter avec succès"))
            return new ResponseEntity<>(result,HttpStatus.OK);

        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }
}
