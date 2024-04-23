package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.EventRepository;
import com.example.garderieapi.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.garderieapi.dto.EventDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface EventServiceImpl  {

    List<EventDto> getAllEvents();

    EventDto getEventById(Long id);

    String createEvent(EventDto eventDto);

    String updateEvent(Long id, EventDto eventDto);

    String deleteEvent(Long id);
}
