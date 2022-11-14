package com.baylor.se.project.bearnews.Repository;

import com.baylor.se.project.bearnews.Model.Event;
import com.baylor.se.project.bearnews.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
