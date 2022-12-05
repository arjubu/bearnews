package com.baylor.se.project.bearnews.Controller;



import com.baylor.se.project.bearnews.Controller.dto.CommentDto;
import com.baylor.se.project.bearnews.Controller.dto.EventDto;
import com.baylor.se.project.bearnews.Controller.dto.WebMsgDto;
import com.baylor.se.project.bearnews.Models.Event;
import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.ResponseObjectMappers.ArticleWithUsersObjectMapper;
import com.baylor.se.project.bearnews.Service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/event")
@CrossOrigin("http://localhost:3000")
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    SimpMessagingTemplate template;

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
    public ResponseEntity<?> createEvent(@RequestBody Event event) throws JsonProcessingException{
        ServiceResponseHelper serviceResponseHelper = eventService.createEvent(event);
        ObjectMapper objectMapper = new ObjectMapper();
        WebMsgDto webMsgDto = new WebMsgDto();

        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.OK);
        }
        else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", serviceResponseHelper.getContent());
            webMsgDto.setMessage(objectMapper.writeValueAsString(serviceResponseHelper.getResponseMessage()));
            template.convertAndSend("/topic/newPost", webMsgDto);
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper.getResponseMessage()),HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteEventById/{id}")
        public HttpStatus deleteEvent(@PathVariable("id") Long eventId) {
        eventService.deleteAnEvent(eventId);
        return HttpStatus.OK;
        }
}




