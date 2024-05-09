package com.example.garderieapi.Controller;

import com.example.garderieapi.Service.RessourceService;
import com.example.garderieapi.entity.Ressource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ressources")
public class RessourceController {

    private final RessourceService ressourceService;

    @Autowired
    public RessourceController(RessourceService ressourceService) {
        this.ressourceService = ressourceService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Ressource>> getAllRessources() {
        List<Ressource> ressources = ressourceService.getAllRessources();
        return new ResponseEntity<>(ressources, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Ressource> addRessource(@RequestBody Ressource ressource) {
        Ressource newRessource = ressourceService.addRessource(ressource);
        return new ResponseEntity<>(newRessource, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRessource(@PathVariable Long id) {
        ressourceService.deleteRessource(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Ressource> updateRessource(@PathVariable Long id, @RequestBody Ressource updatedRessource) {
        Ressource ressource = ressourceService.updateRessource(id, updatedRessource);
        if (ressource != null) {
            return new ResponseEntity<>(ressource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
