package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.RoleRepository;
import com.example.garderieapi.Repository.UserRepository;
import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.Role;
import com.example.garderieapi.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService,IUserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;



    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    //------------------------ Auth User ----------------------------------
    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(Email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: "+ Email));

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);
    }


    //------------------------ get user By Nom ----------------------------------
    @Override
    public List<User> getByNom(String Nom) {
        return userRepository.findByNom(Nom);
    }


    //------------------------ get user By Roles Name ----------------------------------
    @Override
    public List<User> getUserByRolesName(String role) {

        return userRepository.findByRolesName(role);
    }

    //------------------------ Create user ----------------------------------
    @Override
    public User createUser(String nom,String prenom, String email ,
                           int numero,String role, String password) {
        if (nom.isEmpty()||prenom.isEmpty()||email.isEmpty()||role.isEmpty()||password.isEmpty())
            throw new IllegalArgumentException("! Échec: il ya un paramètre vide");
        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setNumero(numero);
        user.setPassword(passwordEncoder.encode(password));

        Role roles = roleRepository.findByName(role).get();
        user.setRoles(Collections.singleton(roles));
        //if (role.equals("ROLE_ADMIN"))
           // userRepository.save(user);
        return user;
    }
    //------------------------ save user----------------------------------
    @Override
    public User saveUser(User user) {
         return userRepository.save(user);
    }

    //------------------------ get user By Id ----------------------------------
    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }


    //------------------------ get All user ----------------------------------
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    //------------------------ Update user ----------------------------------
    @Override
    public User updateUser(Long userId, String nom, String prenom, String email, int numero, String password) {
        // Récupérez l'utilisateur existant par ID
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            if (!nom.isEmpty())      existingUser.setNom(nom);
            if (!prenom.isEmpty())   existingUser.setPrenom(prenom);
            if (!email.isEmpty())    existingUser.setEmail(email);
            if (numero>19999999)     existingUser.setNumero(numero);
            if (!password.isEmpty()) existingUser.setPassword(passwordEncoder.encode(password));

            return userRepository.save(existingUser);
        }
        return null;
    }
    //------------------------ Update user Password----------------------------------
    @Override
    public User updatePassword(Long userId, String password, String passwordConfirm) {
        // Récupérez l'utilisateur existant par ID
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            if (!password.isEmpty() && password.equals(passwordConfirm))
                existingUser.setPassword(passwordEncoder.encode(password));
            else throw new IllegalArgumentException("! Échec: vérifier votre mot de passe");

            return userRepository.save(existingUser);
        }else throw new IllegalArgumentException("! Échec: utilisateur introuvable");
    }
    //------------------------ get user By Email ----------------------------------
    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    //------------------------ delete user ----------------------------------
    @Override
    public void deleteUserById(Long userId) {
        // Récupérez l'utilisateur par ID
        Optional<User> User = userRepository.findById(userId);
        if (User.isPresent()) {
            User user = User.get();

            user.getRoles().clear();

            userRepository.deleteById(userId);
        }
    }

    //------------------------ get user Connectee ----------------------------------
    @Override
    public Optional<User> UserConnectee(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return getByEmail(email);
    }

    //------------------------ get Responsable by garderie ----------------------------------
    @Override
    public List<User> getResponsableByGarderie(Garderie garderie){

        return userRepository.findByGarderieRespo(garderie);
    }

    //------------------------ get user By GarderieParent ----------------------------------
    @Override
    public List<User> getByGarderieParent(Garderie garderie) {
        return userRepository.findByGarderieParent(garderie);
    }




}









