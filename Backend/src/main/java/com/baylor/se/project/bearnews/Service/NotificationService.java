package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Model.Notification;
import com.baylor.se.project.bearnews.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    public List<Notification> getAllNotification() {return notificationRepository.findAll();}

}
