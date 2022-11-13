package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
}
