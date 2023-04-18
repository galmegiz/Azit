package com.sun.Azit.repository;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByTitle(String title, Pageable pageable);
    Optional<Event> findById(Long id);
    Event save(Event event);

    Page<Event> findAll(Pageable pageable);
    Page<Event> findByTitleContaining(String keyword, Pageable pageable);
    Page<Event> findByContentContaining(String keyword, Pageable pageable);
    Page<Event> findByStatus(Estatus keyword, Pageable pageable);
    Page<Event> findByFeeLessThanEqual(int keyword, Pageable pageable);
    Page<Event> findByPeopleLimitGreaterThanEqual(int keyword, Pageable pageable);

}
