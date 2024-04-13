package com.example.garderieapi.Service;

import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
   List<User> getByNom(String Nom);

   List<User> getUserByRolesName(String role);

   User createUser(String nom,String prenom,String email ,int numero,String role, String password);

   User saveUser(User user);

   Optional<User> getUserById(Long userId);

   List<User> getAllUsers();

   void deleteUserById(Long userId);

    User updateUser(Long userId, String nom, String prenom, String email, int numero, String password);

    User updatePassword(Long userId, String password, String passwordConfirm);

    Optional<User> getByEmail(String email);
   Optional<User> UserConnectee();

    List<User> getResponsableByGarderie(Garderie garderie);

    List<User> getByGarderieParent(Garderie garderie);



}
