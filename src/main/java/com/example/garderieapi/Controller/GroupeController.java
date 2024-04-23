package com.example.garderieapi.Controller;

import com.example.garderieapi.Service.GroupeService;
import com.example.garderieapi.dto.GroupeDto;
import com.example.garderieapi.entity.Groupe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class GroupeController {

    private final GroupeService groupeService;


    public GroupeController(GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    @PostMapping("/Garderie/CreateGroupe")
    public ResponseEntity<String> createGroupe(@RequestBody GroupeDto groupeDto) {

        if(groupeDto.getNom().isEmpty()) {
            return new ResponseEntity<>("Saisir le nom de groupe !", HttpStatus.BAD_REQUEST);
        } else if (groupeDto.getSalle().isEmpty()) {
            return new ResponseEntity<>("Saisir la salle !", HttpStatus.BAD_REQUEST);
        } else if (groupeDto.getNiveau().isEmpty()) {
            return new ResponseEntity<>("Saisir le niveau !", HttpStatus.BAD_REQUEST);
        }

        Groupe createdGroupe = groupeService.createGroupe(groupeDto.getNom(),groupeDto.getSalle(),groupeDto.getNiveau());

        if (createdGroupe==null) return new ResponseEntity<>("! il ya un probleme de création de groupe", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Le groupe a été créé", HttpStatus.CREATED);
    }




    @PutMapping("/Garderie/UpdateGroupe/{id}")
    public ResponseEntity<String> updateGroupe(@PathVariable Long id, @RequestBody GroupeDto updatedGroupeDto) {

        Groupe updatedGroupe = groupeService.updateGroupe(id,updatedGroupeDto.getNom(),
                updatedGroupeDto.getSalle(),updatedGroupeDto.getNiveau(),updatedGroupeDto.getIdResponsable());

        if (updatedGroupe == null)return new ResponseEntity<>("! il ya un probleme de mettre à jour de Groupe", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("! Le groupe a modifié", HttpStatus.UPGRADE_REQUIRED);
    }
    @GetMapping("/Garderie/GetGroupes")
    public ResponseEntity<List<Groupe>> getGroupeByGarderie (){
        List<Groupe> groupes=groupeService.getGroupeByGarderie();
        return new  ResponseEntity<>(groupes,HttpStatus.OK);
    }

    @GetMapping("/Garderie/Groupes/{groupeId}")
    public ResponseEntity<List<Groupe>> getGroupeById (@PathVariable Long groupeId){
        List<Groupe> groupes=groupeService.getGroupeByGarderie();
        return new  ResponseEntity<>(groupes,HttpStatus.FOUND);
    }

    @DeleteMapping("/Garderie/DeleteGroupe/{id}")
    public ResponseEntity<String> deleteGroupe(@PathVariable Long id) {
        String result=groupeService.deleteGroupe(id);
        return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/Garderie/Responsable/{responsableId}/Groupes/")
    public ResponseEntity<List<Groupe>> getGroupeByResponsable(@PathVariable Long responsableId){
        List<Groupe> groupes=groupeService.getGroupeByResponsable(responsableId);
        if (groupes==null)
            return new  ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new  ResponseEntity<>(groupes,HttpStatus.FOUND);
    }

    @GetMapping("/Responsable/Groupes/")
    public ResponseEntity<List<Groupe>> getGroupesForResponsable(){
        List<Groupe> groupes=groupeService.getGroupeByResponsable();
        if (groupes==null)
            return new  ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new  ResponseEntity<>(groupes,HttpStatus.OK);
    }

    @GetMapping("/Parent/enfant/{enfantId}/groupe")
    public ResponseEntity<Groupe> getGroupeForEnfant(@PathVariable Long enfantId){
        Groupe groupe=groupeService.getGroupeForEnfant(enfantId);
        if (groupe==null)
            return new  ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new  ResponseEntity<>(groupe,HttpStatus.FOUND);
    }

    @PatchMapping("/Garderie/groupe/{groupeId}/addResponsable")
    public ResponseEntity<String> addResponsableToGroupe(@PathVariable Long groupeId,@RequestBody Long responsableId){
        String result=groupeService.addResponsableToGroupe(groupeId,responsableId);
        if (result=="Responsable introvable")
            return new  ResponseEntity<>("Responsable introvable",HttpStatus.NOT_FOUND);
        return new  ResponseEntity<>(result,HttpStatus.FOUND);
    }
}
