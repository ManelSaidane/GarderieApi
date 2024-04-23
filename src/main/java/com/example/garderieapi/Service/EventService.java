package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.EventRepository;
import com.example.garderieapi.dto.EventDto;
import com.example.garderieapi.entity.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService  implements  EventServiceImpl {
    private final EventRepository eventRepository;


    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return mapEventListToDtoList(events);
    }

    @Override
    public EventDto getEventById(Long id) {
        Event event = eventRepository.findById(id).orElse(null);
        return mapEventToDto(event);
    }

    @Override
    public String createEvent(EventDto eventDto) {
        Event event = mapDtoToEvent(eventDto);
        eventRepository.save(event);
        return "Événement créé avec succès";
    }

    @Override
    public String updateEvent(Long id, EventDto eventDto) {
        Event existingEvent = eventRepository.findById(id).orElse(null);
        if (existingEvent == null) {
            return "Événement introuvable";
        }
        // Mettre à jour les propriétés de l'événement existant avec les données du DTO
        // Implémenter le mapping manuellement
        // ...
        eventRepository.save(existingEvent);
        return "Événement mis à jour";
    }

    @Override
    public String deleteEvent(Long id) {
        Event existingEvent = eventRepository.findById(id).orElse(null);
        if (existingEvent == null) {
            return "Événement introuvable";
        }
        eventRepository.delete(existingEvent);
        return "Événement supprimé";
    }

    // Méthodes de mapping manuel
    private EventDto mapEventToDto(Event event) {
        if (event == null) {
            return null;
        }
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitre(event.getTitre());
        eventDto.setDate(event.getDate());
        eventDto.setNombreJours(event.getNombreJours());
        eventDto.setResponsable(event.getResponsable());
        eventDto.setDescription(event.getDescription());
        return eventDto;
    }

    private Event mapDtoToEvent(EventDto eventDto) {
        if (eventDto == null) {
            return null;
        }
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setTitre(eventDto.getTitre());
        event.setDate(eventDto.getDate());
        event.setNombreJours(eventDto.getNombreJours());
        event.setResponsable(eventDto.getResponsable());
        event.setDescription(eventDto.getDescription());
        return event;
    }

    private List<EventDto> mapEventListToDtoList(List<Event> events) {
        List<EventDto> eventDtos = new ArrayList<>();
        for (Event event : events) {
            eventDtos.add(mapEventToDto(event));
        }
        return eventDtos;
    }
}