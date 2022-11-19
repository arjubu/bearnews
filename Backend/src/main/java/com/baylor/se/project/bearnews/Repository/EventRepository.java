package com.baylor.se.project.bearnews.Repository;

import com.baylor.se.project.bearnews.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
