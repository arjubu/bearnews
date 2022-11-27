package com.baylor.se.project.bearnews.Controller;



import com.baylor.se.project.bearnews.Models.Event;
import com.baylor.se.project.bearnews.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
    @Autowired
    EventService eventService;


    @PostMapping("/event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event updated = eventService.createEvent(event);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
    }


    @RequestMapping(value = "/allevent", method = RequestMethod.GET)
    public ResponseEntity<Event> getEvents() {
        return new ResponseEntity(eventService.getAllEvent(), HttpStatus.OK);

    }



}


