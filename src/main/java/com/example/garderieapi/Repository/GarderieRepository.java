package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GarderieRepository extends JpaRepository<Garderie,Long>{

   Optional<Garderie> findByGerant(User gerant);
   Optional<Garderie> findByName(String name);
   Optional<Garderie> findById(Long id);
}
