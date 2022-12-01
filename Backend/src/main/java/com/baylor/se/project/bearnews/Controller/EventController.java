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
//@RequestMapping("/api/v1/event")
//@CrossOrigin("*")
public class EventController {

    @Autowired
    EventService eventService;

    @RequestMapping(value = "/allevent", method = RequestMethod.GET)
    public ResponseEntity<Event> getAllEvents() {
        return new ResponseEntity(eventService.getAllEvent(), HttpStatus.OK);

    }


//    @PostMapping("/createEvent")
//    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
//        Event updated = eventService.createEvent(event);
//        return new ResponseEntity(updated, HttpStatus.OK);
//    }

    @RequestMapping(value = "/createEvent", method = RequestMethod.POST)
    public ResponseEntity<?> createEvent(@RequestBody EventDto eventsent) {
        String responseReturned = String.valueOf(eventService.createEvent(eventsent));
        return new ResponseEntity<>(responseReturned,HttpStatus.OK);
    }


    
    @RequestMapping(value = "/deleteEventById", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAnEvent( @RequestParam (name="eventId" , required = true) Long eventId) throws JsonProcessingException {
    String responseReturned = eventService.deleteAnEvent(eventId);
    return new ResponseEntity(responseReturned,HttpStatus.OK);
}

}


