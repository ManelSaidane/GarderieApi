package com.example.garderieapi.Service;


import com.example.garderieapi.Repository.UserRepository;
import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ResponsableService implements IResponsableService{

    private final UserService userService;
    private final GarderieService garderieService;
    private final UserRepository userRepository;


    public ResponsableService(UserService userService, GarderieService garderieService, UserRepository userRepository) {
        this.userService = userService;
        this.garderieService = garderieService;
        this.userRepository = userRepository;

    }
    //------------------------ create Responsable ----------------------------------
    @Override
    public String createResponsable(String nom, String prenom,
                                    String email, int numero,
                                    String password, String role)
    {
        if(!role.equals("ROLE_RESPONSABLE")){
            throw new IllegalArgumentException("! Échec: Vérifier le rôle");
        }
        User user = userService.createUser(nom,prenom,email,numero,role,password);

        Garderie garderie =garderieService.GarderieConnectee();
        if (garderie == null) throw new IllegalArgumentException("! Échec: il ya problème d'autorisation");
        user.setGarderieRespo(garderie);

        User usersaved = userService.saveUser(user);
        if (usersaved == null) throw new IllegalArgumentException("! Échec: il ya problème de création de responsable");
        return "Responsable créé ";
    }


    //------------------------ get all Responsables ----------------------------------
    @Override
    public List<User> getAllResponsableByGarderie() {
        Garderie garderie = garderieService.GarderieConnectee();
        if (garderie == null) {
            // Aucune garderie connectée, retourner une liste vide
            return Collections.emptyList();
        } else {
            // Garderie connectée, retourner les responsables de cette garderie
            return userService.getResponsableByGarderie(garderie);
        }
    }



    //------------------------ update Responsable ----------------------------------
    @Override
    public String updateResponsable(Long id,String nom,String prenom,String email,String password,int numero)
    {
        Garderie garderie=garderieService.GarderieConnectee();
        if (garderie==null) return("! Échec: il ya problème d'autorisation");
        Optional<User> responsable =userRepository.findByIdAndGarderieRespo(id,garderie);
        if(responsable.isPresent()) {
            userService.updateUser(id,nom,prenom,email,numero,password);
            return "Le responsable a été modifié";
        }
        return "Le responsable introuvable";
    }

    //------------------------ Responsable Connectee----------------------------------

    @Override
    public User ResponsableConnectee(){

        String emailConnectee="";
        String roleConnectee="";
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
            emailConnectee = claims.get("sub", String.class);
            roleConnectee = claims.get("role", String.class);
            User user=userService.getByEmail(emailConnectee).get();
            if(user.getGarderieRespo().getValidation() &&
                    roleConnectee.equals("ROLE_RESPONSABLE")){
                return user;
            }
        }
        return null;
    }
    //------------------------ get Responsable by id ----------------------------------

    @Override
    public User getResponsableById(Long id){
        List<User> responsables=getAllResponsableByGarderie();
        Optional<User> user=userRepository.findById(id);
        if (user.isPresent()){
            if (responsables.contains(user.get())){
                return user.get();
            }
        }
        return null;
    }

    //------------------------ Delete Responsable ----------------------------------

    @Override
    public String deleteResponsable(Long id){
        Garderie garderie=garderieService.GarderieConnectee();
        if (garderie==null) throw new IllegalArgumentException("! Échec: il ya problème d'autorisation");
        Optional<User> responsable = userRepository.findByIdAndGarderieRespo(id,garderie);
        if (responsable.isPresent()){
            userService.deleteUserById(id);
            return "Parent supprimé.";
        }
        return null;
    }

}
