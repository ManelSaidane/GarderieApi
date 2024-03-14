package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.UserRepository;
import com.example.garderieapi.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService,IuserService{

    private static UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


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


    @Override
    public List<User> findByNom(String Nom) {
        return userRepository.findByNom(Nom);
    }

    @Override
    public List<User> getUserByRolesName(String role) {

        return userRepository.findByRolesName(role);
    }


    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    @Override
    public void updateUser(Long userId, User updatedUser) {
        // Récupérez l'utilisateur existant par ID
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Mettez à jour les paramètres de l'utilisateur
            existingUser.setNom(updatedUser.getNom());
            existingUser.setPrenom(updatedUser.getPrenom());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setNumero(updatedUser.getNumero());
            existingUser.setPassword(updatedUser.getPassword());

            // Enregistrez les modifications
            userRepository.save(existingUser);
        }
    }


    @Override
    public void deleteUserById(Long userId) {
        // Récupérez l'utilisateur par ID
        Optional<User> User = userRepository.findById(userId);
        if (User.isPresent()) {
            User user = User.get();

            // Supprimez d'abord les associations utilisateur-rôle
            user.getRoles().clear();

            // Puis supprimez l'utilisateur
            userRepository.deleteById(userId);
        }
    }
}









