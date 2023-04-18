package com.sun.Azit.service;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.constant.SearchType;
import com.sun.Azit.entity.Event;
import com.sun.Azit.dto.EventDto;
import com.sun.Azit.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event saveEvent(EventDto eventDto){
        Event newEvent = eventDto.toEntity();
        return eventRepository.save(newEvent);
    }

    public EventDto getEvent(Long eventId){
        return eventRepository.findById(eventId)
                .map(EventDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다"));
    }

    public Page<EventDto> searchEvent(SearchType searchType, String searchKeyword, Pageable pageable){
        if(searchKeyword == null || searchKeyword.isBlank()){
            return eventRepository.findAll(pageable).map(EventDto::from);
        }

        return switch (searchType){
            case TITLE -> eventRepository.findByTitleContaining(searchKeyword, pageable).map(EventDto::from);
            case CONTENT -> eventRepository.findByContentContaining(searchKeyword, pageable).map(EventDto::from);
            case STATUS -> eventRepository.findByStatus(Estatus.valueOf(searchKeyword), pageable).map(EventDto::from);
            case FEE -> eventRepository.findByFeeLessThanEqual(Integer.parseInt(searchKeyword), pageable).map(EventDto::from);
            case PEOPLE_LIMIT -> eventRepository.findByPeopleLimitGreaterThanEqual(Integer.parseInt(searchKeyword), pageable).map(EventDto::from);
            case ID -> null;
        };
    }
    public void updateEvent(long eventId, EventDto Dto){

    }

    public void deleteEvent(long eventId){
        eventRepository.deleteById(eventId);
    }

    public long getEventCount(){
        return eventRepository.count();
    }
}
