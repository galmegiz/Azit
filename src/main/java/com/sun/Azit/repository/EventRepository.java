package com.sun.Azit.repository;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.constant.SearchType;
import com.sun.Azit.dto.EventFormDto;
import com.sun.Azit.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByTitle(String title);

    Page<Event> findByTitleContaining(String searchValue, Pageable pageable);

    Page<Event> findByFeeLessThanEqual(int parseInt, Pageable pageable);

    Page<Event> findByStatus(Estatus estatus, Pageable pageable);

    Page<Event> findByContentContaining(SearchType searchType, Pageable pageable);

    Page<Event> findByPeopleLimitLessThanEqual(int parseInt, Pageable pageable);
}
