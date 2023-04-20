package com.sun.Azit.service;

import com.sun.Azit.dto.EventFormDto;
import com.sun.Azit.entity.Event;
import com.sun.Azit.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;

    public Page<EventFormDto> getEventLists(Pageable pageable){
        return eventRepository.findAll(pageable).map(EventFormDto::from);
    }
    public Event createEvent(Event event){
        return eventRepository.save(event);
    }

    public EventFormDto getEventDetail(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(IllegalStateException::new)
                .toDto();
    }
}
