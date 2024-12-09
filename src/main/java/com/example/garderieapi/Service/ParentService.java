package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.EnfantRepository;
import com.example.garderieapi.Repository.UserRepository;
import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;
import com.example.garderieapi.entity.Enfant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ParentService implements IParentService {

    private final UserService userService;
    private final GarderieService garderieService;
    private final UserRepository userRepository;
    private final EnfantRepository enfantRepository;


    public ParentService(UserService userService, GarderieService garderieService, UserRepository userRepository, EnfantRepository enfantRepository) {
        this.userService = userService;
        this.garderieService = garderieService;
        this.userRepository = userRepository;
        this.enfantRepository = enfantRepository;
    }

    //------------------------ Create Parent----------------------------------
    @Override
    public String createParent(String nom, String prenom,
                               String email, int numero,
                               String password, String role,
                               String enfantNom, String enfantPrenom, String enfantNiveau)
    {
        if(!role.equals("ROLE_PARENT")){
            throw new IllegalArgumentException("! Échec: Vérifier le rôle");
        }
        User user = userService.createUser(nom,prenom,email,numero,role,password);

        Garderie garderie =garderieService.GarderieConnectee();
        if (garderie == null) throw new IllegalArgumentException("! Échec: il ya problème d'autorisation");
        user.setGarderieParent(garderie);

        //--creation Enfant--
        if (enfantNom.isEmpty() || enfantPrenom.isEmpty()||enfantNiveau.isEmpty())
            return "! Échec: Vérifier les paramètre d'enfant" ;
        Enfant enfant= new Enfant();
        enfant.setNom(enfantNom);
        enfant.setPrenom(enfantPrenom);
        enfant.setNiveau(enfantNiveau);
        enfant.setGarderie(garderie);
        enfantRepository.save(enfant);
        //---------------------------
        user.setEnfants(Collections.singleton(enfant));
        userService.saveUser(user);
        return "Parent créé ";
    }


    //------------------------ get All Parent By Garderie ----------------------------------
    @Override
    public List<User> getParentByGarderie() {
        Garderie garderie= garderieService.GarderieConnectee();
        if (garderie==null) throw new IllegalArgumentException("! Votre garderie introuvable.");
        return userService.getByGarderieParent(garderie);
    }


    //------------------------ get Parent By Id ----------------------------------
    @Override
    public User getParentById(Long idParent) {
        Garderie garderie=garderieService.GarderieConnectee();
        if (garderie==null) throw new IllegalArgumentException("! Échec: il ya problème d'autorisation");
        Optional<User> parent=userRepository.findById(idParent);
        if (parent.isPresent()&&parent.get().getGarderieParent().equals(garderie))
            return parent.get();
        return null;
    }
    @Override
    public User ParentConnectee(){
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
            if(user.getGarderieParent().getValidation() &&
                    roleConnectee.equals("ROLE_PARENT")){
                return user;
            }
        }
        return null;
    }

    //------------------------ Update Parent ----------------------------------
    @Override
    public String updateParent(Long idParent, String nom, String prenom, String email, int numero, String password) {
        Garderie garderie=garderieService.GarderieConnectee();
        User parent=ParentConnectee();
        if (garderie==null && parent==null) return("! Échec: il ya problème d'autorisation");
        Optional<User> parentget =userRepository.findByIdAndGarderieParent(idParent,garderie);
        if(parentget.isPresent()) {
            userService.updateUser(idParent,nom,prenom,email,numero,password);
            return "Le responsable a été modifié";
        }
        return "Le responsable introuvable";

    }


    //------------------------ add Enfant To Parent ----------------------------------
    @Override
    public void addEnfantToParent(Long idParent,Enfant enfant) {
        User parent=getParentById(idParent);
        if (parent == null) throw new IllegalArgumentException( "! Échec: Parent introuvable");
        parent.getEnfants().add(enfant);
        userRepository.save(parent);
    }

    //------------------------ Parent Connectee ----------------------------------
    @Override
    public User parentConnectee(){
        User myuser = userService.UserConnectee().get();
        boolean testRole = myuser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_PARENT"));
        if(!testRole){
            throw new IllegalArgumentException("! Votre Garderie introuvable.");
        }
        return myuser;
    }




    //------------------------ Delete Parent ----------------------------------
    @Override
    public String deleteParent (Long idParent){


        Garderie garderie =garderieService.GarderieConnectee();
        Optional<User> parent =userRepository.findByIdAndGarderieParent(idParent,garderie);
        if (garderie!=null && parent.isPresent()) {
            parent.get().getEnfants().clear();
            return "Parent supprimé.";
        }
        return null;
    }

}
