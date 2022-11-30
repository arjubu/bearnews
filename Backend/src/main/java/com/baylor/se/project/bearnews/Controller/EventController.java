package com.baylor.se.project.bearnews.Controller;



import com.baylor.se.project.bearnews.Models.Event;
import com.baylor.se.project.bearnews.ResponseObjectMappers.ArticleWithUsersObjectMapper;
import com.baylor.se.project.bearnews.Service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {
    @Autowired
    EventService eventService;


    @GetMapping
    public ResponseEntity<Event> getAllEvents() {
        return new ResponseEntity(eventService.getAllEvent(), HttpStatus.OK);

    }

    @PostMapping("/save-event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event updated = eventService.createEvent(event);
        return new ResponseEntity(updated, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteEventById", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAnEvent( @RequestParam (name="eventId" , required = true) Long eventId) throws JsonProcessingException {
        String responseReturned = eventService.deleteAnEvent(eventId);
        return new ResponseEntity(responseReturned,HttpStatus.OK);

    }




}


