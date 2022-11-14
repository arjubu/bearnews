package com.baylor.se.project.bearnews.Controller;

import com.baylor.se.project.bearnews.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;
}
