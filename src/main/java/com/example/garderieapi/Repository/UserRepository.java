package com.example.garderieapi.Repository;


import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String Email);

    Boolean existsByEmail(String email);

    List<User> findByNom(String nom);

    List<User> findByRolesName(String role);

    List<User> findByGarderieParent(Garderie garderie);

   List<User> findByGarderieRespo(Garderie garderie);

   Optional<User> findByIdAndGarderieRespo(Long id,Garderie garderie);

    Optional<User> findByIdAndGarderieParent(Long id,Garderie garderie);


}
