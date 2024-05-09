package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.Fourniture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FournitureRepository extends JpaRepository<Fourniture, Long> {
    List<Fourniture> findByNomContainingIgnoreCase(String nom);
}
