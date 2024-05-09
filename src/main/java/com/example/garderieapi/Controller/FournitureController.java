package com.example.garderieapi.Controller;

import com.example.garderieapi.Service.FournitureService;
import com.example.garderieapi.entity.Fourniture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/fournitures")
public class FournitureController {
    @Autowired
    private FournitureService fournitureService;

    @GetMapping("/allfournitures")
    public List<Fourniture> getAllFournitures() {
        return fournitureService.getAllFournitures();
    }

    @PostMapping("/createfourniture")
    public Fourniture addFourniture(@RequestBody Fourniture fourniture) {
        return fournitureService.addFourniture(fourniture);
    }

    @DeleteMapping("/deletefourniture/{id}")
    public void removeFourniture(@PathVariable Long id) {
        fournitureService.removeFourniture(id);
    }

    @PutMapping("/updatefourniture/{id}")
    public Fourniture updateFourniture(@PathVariable Long id, @RequestBody Fourniture fourniture) {
        return fournitureService.updateFourniture(id, fourniture);
    }
    @GetMapping("/fournituresParNom/{nom}")
    public List<Fourniture> getFournituresParNom(@PathVariable String nom) {
        return fournitureService.getFournituresParNom(nom);
    }

}
