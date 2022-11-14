package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

}
