package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // Ajoutez des méthodes personnalisées si nécessaire
}

