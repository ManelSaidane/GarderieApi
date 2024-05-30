package com.example.garderieapi.Controller;


import com.example.garderieapi.Config.JwtTokenProvider;
import com.example.garderieapi.Service.ActiviteService;
import com.example.garderieapi.Service.ResponsableService;
import com.example.garderieapi.dto.ActiviteDto;
import com.example.garderieapi.entity.Absence;
import com.example.garderieapi.entity.Activite;
import com.example.garderieapi.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/")
public class ActiviteController {

    private final ActiviteService activiteService;

    public ActiviteController(ActiviteService activiteService) {
        this.activiteService = activiteService;
        ;
    }

    @DeleteMapping({"Garderie/Activite/{id}","Responsable/Activite/{id}"})
    public ResponseEntity<String> deleteActiviteById(@PathVariable Long id) {
        String result= activiteService.deleteActivite(id);
        if (result.equals("l'activité supprimer avec succès"))
            return new ResponseEntity<>(result, HttpStatus.OK);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }



    @GetMapping({"Garderie/Activite/{id}","Responsable/Activite/{id}"})
    public ResponseEntity<Activite> getActiviteById(@PathVariable Long id) {
        Activite activite= activiteService.getActiviteById(id);
        if (activite==null) return new ResponseEntity<>(activite, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(activite, HttpStatus.OK);
    }

    @GetMapping({"Garderie/Activite","Responsable/Activite"})
    public ResponseEntity<List<Activite> > getAllActivite() {
        List<Activite> activites= activiteService.getAllActivites();
        if (activites==null) return new ResponseEntity<>(activites, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(activites, HttpStatus.OK);
    }

    @PutMapping({"Garderie/Activite/{id}/update","Responsable/Activite/{id}/update"})
    public ResponseEntity<String> getActiviteById(@PathVariable Long id,@RequestBody ActiviteDto activiteDto) {
        String result = activiteService.updateActivite(id,activiteDto.getNom(),activiteDto.getDate(),activiteDto.getHeure());
        if (result.equals("Activité mise à jour avec succès")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @PostMapping({"Garderie/createActivite","Responsable/createActivite"})
    public ResponseEntity<String> createActivite(@RequestBody ActiviteDto activiteDto) {

        String result = activiteService.createActivite(activiteDto.getNom(),activiteDto.getDate(),activiteDto.getHeure());
        if (!result.equals("Activité créée avec succès")) return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
