package com.example.garderieapi.Controller;

import com.example.garderieapi.Repository.UserRepository;
import com.example.garderieapi.Service.UserService;

import com.example.garderieapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // build create User REST API
    @PostMapping("/Create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id){
        //User user = userService.getUserById(id);
        return userService.getUserById(id);
    }

    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping("/ListUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Build Update User REST API
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
        return ResponseEntity.ok("Paramètres de l'utilisateur mis à jour avec succès !");
    }

    // Build Delete User REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User successfully deleted by ID!");
    }
}

