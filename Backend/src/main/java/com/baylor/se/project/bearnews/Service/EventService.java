package com.baylor.se.project.bearnews.Service;


import com.baylor.se.project.bearnews.Controller.dto.CommentDto;
import com.baylor.se.project.bearnews.Controller.dto.EventDto;
import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Models.Comment;
import com.baylor.se.project.bearnews.Models.Event;

import com.baylor.se.project.bearnews.Models.Event;

import com.baylor.se.project.bearnews.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

//    public String createEvent(EventDto eventDto) {
//        Event event = new Event();
//        event.setTitle(eventDto.geteventTitle());
//        event.setDescription(eventDto.geteventdescription());
//        event.setLocation(eventDto.geteventlocation());
//        eventRepository.save(event);
//        return "inserted";
//
//    }

    public Event createEvent(Event event){
        return eventRepository.save(event);
    }

    public List<Event> getAllEvent() {return eventRepository.findAll();}
    
    
    public String deleteAnEvent(Long id){
    if(fetchEvenet(id)==null)
        return "event id doesn't exsists";
    else{
        eventRepository.deleteById(id);
        return "deleted successfully";
    }
}
public Event fetchEvenet(Long id) {
    Optional<Event> eventQueryOpt = eventRepository.findById(id);
    if (eventQueryOpt.isPresent())
        return eventQueryOpt.get();
    return null;
}



}


