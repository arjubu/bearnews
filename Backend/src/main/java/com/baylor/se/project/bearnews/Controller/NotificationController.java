package com.baylor.se.project.bearnews.Controller;


import com.baylor.se.project.bearnews.Models.Event;
import com.baylor.se.project.bearnews.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;


}
