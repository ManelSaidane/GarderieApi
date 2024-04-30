package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.Fourniture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournitureRepository extends JpaRepository<Fourniture, Long> {
}
