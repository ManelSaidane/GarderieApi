package com.example.garderieapi.Controller;


import com.example.garderieapi.Config.JwtTokenProvider;
import com.example.garderieapi.Service.ActiviteService;
import com.example.garderieapi.Service.ResponsableService;
import com.example.garderieapi.dto.ActiviteDto;
import com.example.garderieapi.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activites")
public class ActiviteController {

    private final ActiviteService activiteService;
    private final JwtTokenProvider jwtTokenProvider;

    private final ResponsableService responsableService;
    public ActiviteController(ActiviteService activiteService, JwtTokenProvider jwtTokenProvider, ResponsableService responsableService) {
        this.activiteService = activiteService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.responsableService = responsableService;
    }

 /*   @GetMapping("/garderie")
    public ResponseEntity<List<Activite>> getActivitesByGarderieId(@RequestHeader("Authorization") String token) {
        // Extraire l'ID de la garderie du token JWT
        Long garderieId = jwtTokenProvider.extractGarderieIdFromToken(token);

        // Récupérer les activités associées à la garderie
        List<Activite> activites = activiteService.getActivitesByGarderieId(garderieId);
        return new ResponseEntity<>(activites, HttpStatus.OK);
    }*/

    @GetMapping("/listeActivite")
    public ResponseEntity<List<ActiviteDto>> getAllActivites() {
        List<ActiviteDto> activites = activiteService.getAllActivites();
        return new ResponseEntity<>(activites, HttpStatus.OK);
    }

    @GetMapping("/getone/{id}")
    public ResponseEntity<ActiviteDto> getActiviteById(@PathVariable Long id) {
        ActiviteDto activite = activiteService.getActiviteById(id);
        return activite != null ? new ResponseEntity<>(activite, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createActivite")
    public ResponseEntity<String> createActivite(@RequestHeader("Authorization") String token, @RequestBody ActiviteDto activiteDto) {
        // Obtenir l'utilisateur responsable connecté
        User responsable = responsableService.ResponsableConnectee();

        // Vérifier si l'utilisateur responsable est valide
        if (responsable != null) {
            // Récupérer l'ID du responsable connecté
            Long idResponsable = responsable.getId();

            // Ajouter l'ID du responsable connecté à l'objet ActiviteDto
            activiteDto.setResponsableId(idResponsable);

            // Appeler le service pour créer l'activité
            String result = activiteService.createActivite(activiteDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            // Retourner une réponse indiquant que l'utilisateur n'est pas autorisé
            return new ResponseEntity<>("Utilisateur non autorisé", HttpStatus.UNAUTHORIZED);
        }
    }



    @PutMapping("/updateActivite/{id}")
    public ResponseEntity<String> updateActivite(@PathVariable Long id, @RequestBody ActiviteDto activiteDto) {
        String result = activiteService.updateActivite(id, activiteDto);
        return result.equals("Activité mise à jour") ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/supprimerActivite/{id}")
    public ResponseEntity<String> deleteActivite(@PathVariable Long id) {
        String result = activiteService.deleteActivite(id);
        return result.equals("Activité supprimée") ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}
