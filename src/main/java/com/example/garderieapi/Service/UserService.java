package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.GroupeRepository;
import com.example.garderieapi.Repository.RoleRepository;
import com.example.garderieapi.Repository.UserRepository;
import com.example.garderieapi.Repository.UserRoleRepositoryImpl;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService, IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private  final GroupeRepository groupeRepository;
    private final UserRoleRepositoryImpl userRoleRepository; // Correction du nom ici

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, GroupeRepository groupeRepository, UserRoleRepositoryImpl userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.groupeRepository = groupeRepository;
        this.userRoleRepository = userRoleRepository; // Correction ici
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
    //------------------------ get user name  By Roles Name ----------------------------------
    public String getUsernameByRole(String role) {
        // Logique pour récupérer le nom d'utilisateur en fonction du rôle
        // Cela pourrait impliquer une requête à votre base de données ou une autre source de données
        // Par exemple, supposons une simple logique où nous avons une liste de rôles associés à des noms d'utilisateur
        Map<String, String> roleToUsernameMap = new HashMap<>();
        roleToUsernameMap.put("ROLE_ADMIN", "adminUser");
        roleToUsernameMap.put("ROLE_USER", "regularUser");

        // Si le rôle existe dans la carte, retournez le nom d'utilisateur correspondant
        if (roleToUsernameMap.containsKey(role)) {
            return roleToUsernameMap.get(role);
        } else {
            // Si le rôle n'existe pas, retournez null ou une autre valeur par défaut selon votre logique métier
            return null;
        }
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
    public  Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }


    //------------------------ get All user ----------------------------------
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


//--- get role by email ---
    public String getRoleByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Set<Role> roles = user.getRoles();

            if (!roles.isEmpty()) {
                Role role = roles.iterator().next();
                return role.getName();
            } else {
                return null; // Aucun rôle trouvé pour cet utilisateur
            }
        } else {
            return null; // Aucun utilisateur trouvé avec cet email
        }
    }

    //----- get Role by  username -----
    @Override
    public String getRoleByUsername(String nom) {
        User user = userRepository.findByNom(nom).stream().findFirst().orElse(null);


        if (user != null && user.getRoles() != null && !user.getRoles().isEmpty()) {
            Role role = user.getRoles().iterator().next(); // Sélectionner le premier rôle de l'utilisateur
            return role.getName();
        } else {
            return null;
        }
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
            groupeRepository.deleteByResponsablesId(userId);
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


    //----------------add user to socket channel by id ------------------------------//

  /* Transactional

    public void addUser(Long userId, String socketId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                user.setSocketId(socketId);
                userRepository.save(user);
                System.out.println("User " + user.getId() + " connected with socket ID " + user.getSocketId());
            } else {
                System.out.println("User with ID " + userId + " not found.");
            }
        } catch (Exception e) {
            System.err.println("Failed to connect user: " + e.getMessage());
            throw new RuntimeException("Failed to connect user", e);
        }
    }
    //----------------remove user to socket channel by id ------------------------------//


    @Transactional
    public void removeUser(String socketId) {
        try {
            User user = userRepository.findBySocketId(socketId).orElse(null);
            if (user != null) {
                user.setSocketId(null);
                userRepository.save(user);
                System.out.println("User " + user.getId() + " disconnected");
            } else {
                System.out.println("User with socket ID " + socketId + " not found.");
            }
        } catch (Exception e) {
            System.err.println("Failed to disconnect user: " + e.getMessage());
            throw new RuntimeException("Failed to disconnect user", e);
        }
    }*/


}









