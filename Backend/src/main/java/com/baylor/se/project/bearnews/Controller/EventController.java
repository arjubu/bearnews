package com.baylor.se.project.bearnews.Controller;



import com.baylor.se.project.bearnews.Controller.dto.CommentDto;
import com.baylor.se.project.bearnews.Controller.dto.EventDto;
import com.baylor.se.project.bearnews.Models.Event;
import com.baylor.se.project.bearnews.Models.Tag;
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
@CrossOrigin("http://localhost:3000")
public class EventController {

    @Autowired
    EventService eventService;

@GetMapping("/getAllEvent")
public List<Event> getAllEvent()
{
    return eventService.getAllEvent();
}
//    @PostMapping("/createEvent")
//    public ResponseEntity<?> createEvent(@RequestBody EventDto eventsent) {
//        String responseReturned = String.valueOf(eventService.createEvent(eventsent));
//        return new ResponseEntity<>(responseReturned,HttpStatus.OK);
//    }

    @PostMapping("/createEvent")
    public Event createEvent(@RequestBody Event event){
    return eventService.createEvent(event);
    }

    @DeleteMapping("/deleteEventById/{id}")
        public HttpStatus deleteEvent(@PathVariable("id") Long eventId) {
        eventService.deleteAnEvent(eventId);
        return HttpStatus.OK;
        }
}




