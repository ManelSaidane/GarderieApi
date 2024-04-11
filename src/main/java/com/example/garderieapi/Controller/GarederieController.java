package com.example.garderieapi.Controller;

import com.example.garderieapi.Service.GarderieService;
import com.example.garderieapi.Service.UserService;
import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1")
public class GarederieController {
    private final GarderieService garderieService;
    private final UserService userService;

    public GarederieController(GarderieService garderieService, UserService userService) {
        this.garderieService = garderieService;
        this.userService = userService;
    }
    @GetMapping("/Admin/allGarderie")
    public ResponseEntity<List<Garderie>> getAllGardedrie(){

        List<Garderie> allGarderie= garderieService.getAllGarderie();
        if (allGarderie.isEmpty()) return new ResponseEntity<>(allGarderie, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(allGarderie, HttpStatus.FOUND);
    }

    @GetMapping("/Admin/AllGerants")
    public ResponseEntity<List<User>> getAllGerantsWithGardedrie(){
        
        List<User> gerants = userService.getUserByRolesName("ROLE_GARDERIE");
        if (gerants.isEmpty()) return new ResponseEntity<>(gerants, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(gerants, HttpStatus.FOUND);
    }

    @GetMapping({"/Admin/Garderie/{garderieId}","/Garderie/{garderieId}"})
    public ResponseEntity<Garderie> getGarderieById(@PathVariable Long garderieId){

        Garderie garderie=garderieService.getGarderieById(garderieId);
        if (garderie==null) return new ResponseEntity<>(garderie, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(garderie, HttpStatus.FOUND);
    }

    @GetMapping("/Admin/Garderies")
    public ResponseEntity<Garderie> getGarderieById(@RequestBody String nomGarderie){

        Garderie garderie=garderieService.getGarderieByNom(nomGarderie);
        if (garderie==null) return new ResponseEntity<>(garderie, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(garderie, HttpStatus.FOUND);
    }

    @GetMapping("/Admin/AllGerants")
    public ResponseEntity<List<User>> getGerants(){

        List<User> garderies=garderieService.getGerants();
        if (garderies==null) return new ResponseEntity<>(garderies, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(garderies, HttpStatus.FOUND);
    }
    @DeleteMapping("/Admin/Garderie/{garderieId}")
    public ResponseEntity<String> deleteGardderie(@PathVariable Long garderieId){

        String result=garderieService.deleteGarderir(garderieId);
        if (result.equals("Le garderie introuvable")) return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping("/Admin/Garderie/{garderieId}/update")
    public ResponseEntity<String> getGerants(@PathVariable Long garderieId,@RequestBody String nouveauNom){

        String result=garderieService.updateGarderie(garderieId,nouveauNom);
        if (result.equals("Le garderie introuvable")) return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.UPGRADE_REQUIRED);
    }



}
