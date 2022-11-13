package com.baylor.se.project.bearnews.Repository;

import com.baylor.se.project.bearnews.Model.Event;
import com.baylor.se.project.bearnews.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
