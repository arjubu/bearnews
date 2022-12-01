package com.baylor.se.project.bearnews.Service;


import com.baylor.se.project.bearnews.Models.Article;
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



    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvent() {return eventRepository.findAll();}


}


