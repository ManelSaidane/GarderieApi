package com.example.garderieapi.Repository;


import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yaml.snakeyaml.tokens.Token;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);


    Boolean existsByEmail(String email);

    List<User> findByNom(String nom);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findByRolesName(@Param("roleName") String roleName);
    List<User> findByGarderieParent(Garderie garderie);

   List<User> findByGarderieRespo(Garderie garderie);

   Optional<User> findByIdAndGarderieRespo(Long id,Garderie garderie);

    Optional<User> findByIdAndGarderieParent(Long id,Garderie garderie);

//*    Optional<User> findBySocketId(String socketId);



}
