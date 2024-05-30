package com.example.garderieapi.Controller;

import com.example.garderieapi.Service.ParentService;

import com.example.garderieapi.dto.ParentDto;
import com.example.garderieapi.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1")
public class ParentController {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }




    @PostMapping("/Garderie/Parent/Create")
    public ResponseEntity<String> createParent(@RequestBody ParentDto request) {
        try {
            String result = parentService.createParent(
                    request.getNom(),
                    request.getPrenom(),
                    request.getEmail(),
                    request.getNumero(),
                    request.getPassword(),
                    "ROLE_PARENT"
            );
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/Garderie/GetParents")
    public ResponseEntity<List<User>> GetParents(){
        List<User> parnts= parentService.getParentByGarderie();
       if (parnts.isEmpty()) return new ResponseEntity<>(parnts, HttpStatus.NOT_FOUND);
       return new ResponseEntity<>(parnts, HttpStatus.OK);
    }
    @DeleteMapping("/Garderie/DeleteParent/{idParent}")
    public ResponseEntity<String> DeleteParent(@PathVariable Long idParent){
        return new ResponseEntity<>(parentService.deleteParent(idParent), HttpStatus.OK);
    }

    @PutMapping({"/Garderie/Parent/{idParent}/UpdateParent","/Parent/{idParent}/UpdateParent"})
    public ResponseEntity<String> updateParent(@PathVariable Long idParent, @RequestBody ParentDto parentDto){
        return new ResponseEntity<>(parentService.updateParent(idParent,parentDto.getNom(),parentDto.getPrenom(),
                parentDto.getEmail(),parentDto.getNumero(),parentDto.getPassword()), HttpStatus.OK);
    }


}
