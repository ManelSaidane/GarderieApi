package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.GarderieRepository;
import com.example.garderieapi.Repository.UserRepository;
import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;
import org.springframework.stereotype.Service;

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
                                String nomGarderie)
    {
        if(!role.equals("ROLE_GARD")){
            throw new IllegalArgumentException("! Échec: Vérifier le rôle");
        }
        User gerant=userService.createUser(nom,prenom,email ,numero,password,role);
        userService.saveUser(gerant);
        Garderie garderie = new Garderie();
        garderie.setName(nomGarderie);
        garderie.setGerant(gerant);
        garderieRepository.save(garderie);
        //user.setGarderie(garderieRepository.save(garderie));

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
        return userRepository.findByRolesName("ROLE_GARDERIE");
    }

    //------------------------ Get la Garderie Connectee ----------------------------------
    @Override
    public Garderie GarderieConnectee(){
        Optional<User> myuser = userService.UserConnectee();
        if (myuser.isPresent()){
            boolean testRole = myuser.get().getRoles().stream().
                    anyMatch(role -> role.getName().equals("ROLE_GARD"));

            if(!testRole)throw new IllegalArgumentException("! Votre Garderie introuvable.");

            if (garderieRepository.findByGerant(myuser.get()).isPresent())
                return garderieRepository.findByGerant(myuser.get()).get();
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
}
