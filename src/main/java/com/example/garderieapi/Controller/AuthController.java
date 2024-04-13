package com.example.garderieapi.Controller;


import com.example.garderieapi.Repository.UserRepository;
import com.example.garderieapi.Service.*;

import com.example.garderieapi.dto.LoginDto;
import com.example.garderieapi.dto.SignUpDto;
import com.example.garderieapi.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final UserService userService;

    private final GarderieService garderieService;

    private final ParentService parentService;
    private final ResponsableService responsableService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, UserService userService, GarderieService garderieService, ParentService parentService, ResponsableService responsableService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
        this.garderieService = garderieService;
        this.parentService = parentService;
        this.responsableService = responsableService;
    }

    @PostMapping("/Auth/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Connexion réussie !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'authentification : " + e.getMessage());
        } }
    @PostMapping("/Admin/create_user")
    public ResponseEntity<?> registerGard(@RequestBody SignUpDto signUpDto){

        if (signUpDto.getNomGarderie().isEmpty())
            return new ResponseEntity<>("Saisir le nom de garderie !", HttpStatus.BAD_REQUEST);

        if(userRepository.existsByEmail(signUpDto.getEmail()))
            return new ResponseEntity<>("! Email c'est déjà existé", HttpStatus.BAD_REQUEST);

        if(!signUpDto.getPassword().equals(signUpDto.getPasswordConfirm()))
            return new ResponseEntity<>("! Vérifiez le mot de passe", HttpStatus.BAD_REQUEST);

        if (!signUpDto.getRole().equals("ROLE_ADMIN") && !signUpDto.getRole().equals("ROLE_GARD"))
            return new ResponseEntity<>("! Vérifiez le rôle ", HttpStatus.BAD_REQUEST);



        if (signUpDto.getRole().equals("ROLE_GARD")) {
            garderieService.creteGarderie(signUpDto.getNom(),signUpDto.getPrenom(),
                    signUpDto.getEmail(),signUpDto.getNumero(),signUpDto.getPassword(),
                    signUpDto.getRole(),signUpDto.getNomGarderie(),true);
            return new ResponseEntity<>("Garderie créé", HttpStatus.CREATED);
        }else {
            User user=userService.createUser(signUpDto.getNom(),signUpDto.getPrenom(),
                    signUpDto.getEmail(),signUpDto.getNumero(),signUpDto.getPassword(),
                    signUpDto.getRole());
            if (user == null) return new ResponseEntity<>("! il ya problème de création Admin", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Admin créé", HttpStatus.CREATED);
    }


    @PostMapping("/CreateGarderie")
    public ResponseEntity<?> registerGarderie(@RequestBody SignUpDto signUpDto){

        if (signUpDto.getNomGarderie().isEmpty())
            return new ResponseEntity<>("Saisir le nom de garderie !", HttpStatus.BAD_REQUEST);

        if(userRepository.existsByEmail(signUpDto.getEmail()))
            return new ResponseEntity<>("! Email c'est déjà existé", HttpStatus.BAD_REQUEST);

        if(!signUpDto.getPassword().equals(signUpDto.getPasswordConfirm()))
            return new ResponseEntity<>("! Vérifiez le mot de passe", HttpStatus.BAD_REQUEST);





        String result=garderieService.creteGarderie(signUpDto.getNom(),signUpDto.getPrenom(),
                signUpDto.getEmail(),signUpDto.getNumero(),signUpDto.getPassword(),
                "ROLE_GARD",signUpDto.getNomGarderie(),false);
        if (result.equals("Garderie créé")) return new ResponseEntity<>(result, HttpStatus.CREATED);
        return new ResponseEntity<>("Admin créé", HttpStatus.CREATED);
    }

    @PostMapping("/Garderie/create_user")
    public ResponseEntity<?> registerRespoOrParent(@RequestBody SignUpDto signUpDto){


        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        String resultat="";

        if (!signUpDto.getRole().equals("ROLE_RESPONSABLE") && !signUpDto.getRole().equals("ROLE_PARENT"))
            return new ResponseEntity<>("Vérifiez le rôle !", HttpStatus.BAD_REQUEST);

        if (signUpDto.getRole().equals("ROLE_RESPONSABLE")){
            resultat=responsableService.createResponsable(signUpDto.getNom(),signUpDto.getPrenom(),
                    signUpDto.getEmail(),signUpDto.getNumero(),signUpDto.getPassword(),
                    signUpDto.getRole());
        }else
        {
            resultat=parentService.createParent(signUpDto.getNom(),signUpDto.getPrenom(),
                    signUpDto.getEmail(),signUpDto.getNumero(),signUpDto.getPassword(),
                    signUpDto.getRole(),signUpDto.getNomEnfant(),signUpDto.getPrenomEnfant(),
                    signUpDto.getNiveauEnfant());
        }

        return new ResponseEntity<>(resultat, HttpStatus.OK);

    }
}
