package com.sun.Azit.repository;

import com.sun.Azit.entity.Event;
import com.sun.Azit.entity.EventImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventImgRepository extends JpaRepository<EventImg, Long> {
    Optional<EventImg> findByEvent(Event event);
}
