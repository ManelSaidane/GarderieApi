package com.example.garderieapi.Service;

import com.example.garderieapi.entity.User;

import java.util.List;
import java.util.Optional;

public interface IuserService {
   List<User> findByNom(String Nom);
   List<User> getUserByRolesName(String role);

   User createUser(User user);

   Optional<User> getUserById(Long userId);

   List<User> getAllUsers();

   void deleteUserById(Long userId);

   void updateUser(Long userId, User updatedUser);


}
