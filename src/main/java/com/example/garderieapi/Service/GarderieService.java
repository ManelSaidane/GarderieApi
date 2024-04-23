package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.GarderieRepository;
import com.example.garderieapi.Repository.UserRepository;
import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class GarderieService implements IGarderieService {


    private final GarderieRepository garderieRepository;

    private final UserService userService;
    private final UserRepository userRepository;

    public GarderieService(GarderieRepository garderieRepository, UserService userService, UserRepository userRepository) {
        this.garderieRepository = garderieRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }
    //------------------------ create Garderie ----------------------------------
    @Override
    public String creteGarderie(String nom        , String prenom,
                                String email      , int numero,
                                String password   , String role,
                                String nomGarderie,Boolean validation)
    {
        if(!role.equals("ROLE_GARD")){
            throw new IllegalArgumentException("! Échec: Vérifier le rôle");
        }
        User gerant=userService.createUser(nom,prenom,email ,numero,role,password);
        userRepository.save(gerant);
        Garderie garderie = new Garderie();
        garderie.setName(nomGarderie);
        garderie.setGerant(gerant);
        garderie.setValidation(validation);
        garderieRepository.save(garderie);
       // user.setGarderie(garderieRepository.save(garderie));

        return "Garderie créé";
    }

    //------------------------ Get all Garderies  ----------------------------------
    @Override
    public List<Garderie> getAllGarderie() {

        return garderieRepository.findAll();
    }

    //------------------------ Get Garderies By nom ----------------------------------
    @Override
    public Garderie getGarderieByNom(String nomGarderie) {
        Optional<Garderie> garderie=garderieRepository.findByName(nomGarderie);
        if (garderie.isPresent()) return garderie.get();
        return null;
    }


    //------------------------ Get Garderie By id ----------------------------------
    @Override
    public Garderie getGarderieById(Long gerantId) {

        Garderie garderie = null;
        Optional<User> gerant=userRepository.findById(gerantId);
        if (gerant.isPresent()){
            garderie = garderieRepository.findByGerant(gerant.get()).get();
        }

        return garderie;
    }


    //------------------------ Get all gerants  ----------------------------------
    @Override
    public List<User> getGerants()
    {
        return userRepository.findByRolesName("ROLE_GARD");
    }

    //------------------------ Get la Garderie Connectee ----------------------------------
    @Override
    public Garderie GarderieConnectee(){
        Long garderieId=0L;
        String emailConnectee="";
        String secretKey = "mySecretKey";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // Extraire le token JWT du header "Authorization"
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Extraire le nom d'utilisateur du token JWT
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            garderieId = claims.get("garderieId", Long.class);
            emailConnectee = claims.get("sub", String.class);
            Garderie garderie = garderieRepository.findById(garderieId).get();
            if (emailConnectee.equals(garderie.getGerant().getEmail())) {
                return garderie;
            }
        }
        return null;
    }

    //------------------------ Delete Garderie  ----------------------------------
    @Override
    public String deleteGarderir(Long garderieId) {
        Optional<Garderie> garderie = garderieRepository.findById(garderieId);
        if (garderie.isPresent()) {
            garderieRepository.delete(garderie.get());
            return "Le garderie supprimer avec succès";
        }
        return "Le garderie introuvable";
    }



    //------------------------ update Garderie  ----------------------------------
    @Override
    public String updateGarderie(Long garderieId,String nomGarderie)
    {
        Optional<Garderie> garderieExiste = garderieRepository.findById(garderieId);
        if (garderieExiste.isPresent()){
            garderieExiste.get().setName(nomGarderie);

        garderieRepository.save(garderieExiste.get());

        return "Garderie modifée";
        }
        return "Le garderie introuvable";
    }
    //------------------------ verification Garderie  ----------------------------------
    @Override
    public String verificationGarderie(Long garderieId)
    {

            Optional<Garderie> garderieExiste = garderieRepository.findById(garderieId);
            if (garderieExiste.isPresent()){
                garderieExiste.get().setValidation(!garderieExiste.get().getValidation());

                garderieRepository.save(garderieExiste.get());

                return "La validité a été modifiée";
            }
            return "Le garderie introuvable";

    }

    @Override
    public String updateGarderieValidation(Long garderieId) {
        Optional<Garderie> garderieExiste = garderieRepository.findById(garderieId);
        if (garderieExiste.isPresent()) {
            Garderie garderie = garderieExiste.get();
            garderie.setValidation(!garderie.getValidation());
            garderieRepository.save(garderie);
            return "La validité a été modifiée";
        }
        return "Le garderie introuvable";
    }
    //------------------------ get Garderie by verification  ----------------------------------
    @Override
    public List<Garderie> getGarderieByVerification(Boolean valid) {

        return garderieRepository.findByValidation(valid);

    }

}
