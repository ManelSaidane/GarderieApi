package com.example.garderieapi.Controller;

import com.example.garderieapi.entity.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.garderieapi.dto.EventDto;
import com.example.garderieapi.Service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/getAllEvent")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/getevent/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        EventDto event = eventService.getEventById(id);
        if (event != null) {
            return new ResponseEntity<>(event, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/CreateEvent/")
    public ResponseEntity<String> createEvent(@RequestBody EventDto eventDto) {
        String result = eventService.createEvent(eventDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto) {
        String result = eventService.updateEvent(id, eventDto);
        if (result.equals("Événement mis à jour")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        String result = eventService.deleteEvent(id);
        if (result.equals("Événement supprimé")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}


