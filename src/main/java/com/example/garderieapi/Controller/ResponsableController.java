package com.example.garderieapi.Controller;


import com.example.garderieapi.Service.ResponsableService;
import com.example.garderieapi.dto.UpdateResponsableDto;
import com.example.garderieapi.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ResponsableController {

    private final ResponsableService responsableService;

    public ResponsableController(ResponsableService responsableService) {
        this.responsableService = responsableService;
    }


    @GetMapping("/Garderie/GetAllResponsable")
    public ResponseEntity<List<User>> GetAllResponsable() {
        List<User> responsables=responsableService.getAllResponsableByGarderie();
        if (responsables.isEmpty()) return new  ResponseEntity<>(responsables,HttpStatus.NOT_FOUND);
        return new  ResponseEntity<>(responsables,HttpStatus.FOUND);
    }
    @PutMapping("/Garderie/UpdateResponsable/{idResponsable}")
    public ResponseEntity<String> Update1Responsable(@PathVariable Long idResponsable,
                                                    @RequestBody UpdateResponsableDto updateResponsableDto) {

        String result= responsableService.updateResponsable(
                            idResponsable,
                            updateResponsableDto.getNom(),
                            updateResponsableDto.getPrenom(),
                            updateResponsableDto.getEmail(),
                            updateResponsableDto.getPassword(),
                            updateResponsableDto.getNumero()
                        );
        return new  ResponseEntity<>(result,HttpStatus.UPGRADE_REQUIRED);
    }

    @PutMapping("/Responsable/UpdateResponsable/{idResponsable}")
    public ResponseEntity<String> Update2Responsable(@PathVariable Long idResponsable,
                                                    @RequestBody UpdateResponsableDto updateResponsableDto) {

        String result= responsableService.updateResponsable(
                idResponsable,
                updateResponsableDto.getNom(),
                updateResponsableDto.getPrenom(),
                updateResponsableDto.getEmail(),
                updateResponsableDto.getPassword(),
                updateResponsableDto.getNumero()
        );
        return new  ResponseEntity<>(result,HttpStatus.UPGRADE_REQUIRED);
    }

    @DeleteMapping("/Garderie/DeleteResponsable/{idResponsable}")
    public ResponseEntity<String> DeleteResponsable(@PathVariable Long idResponsable) {

        String result= responsableService.deleteResponsable(idResponsable);
        if (result==null) return new ResponseEntity<>("Responsable introuvable",HttpStatus.NOT_FOUND);
        return new  ResponseEntity<>(result,HttpStatus.OK);
    }

}
