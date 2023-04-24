package com.sun.Azit.service;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.dto.EventFormDto;
import com.sun.Azit.dto.EventImgDto;
import com.sun.Azit.entity.Event;
import com.sun.Azit.repository.EventImgRepository;
import com.sun.Azit.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventImgService eventImgService;

    public Page<EventFormDto> getEventLists(Pageable pageable){
        return eventRepository.findAll(pageable).map(EventFormDto::from);
    }
    public Event createEvent(EventFormDto eventFormDto){
        Event newEvent = Event.of(eventFormDto.getTitle(),
                eventFormDto.getTitleTag(),
                eventFormDto.getRecruitDeadline(),
                eventFormDto.getFee(),
                eventFormDto.getPeopleLimit(),
                eventFormDto.getSummary(),
                eventFormDto.getContent(),
                Estatus.OPEN_SOON,
                eventFormDto.getHashTag(),
                eventFormDto.getStartDate(),
                eventFormDto.getEndDate());
        return eventRepository.save(newEvent);
    }

    public EventFormDto getEventDetail(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> {
                            throw new EntityNotFoundException("이벤트가 존재하지 않거나, 삭제되었습니다.");
                })
                .toDto();
    }

    public Long updateEvent(Long id, EventFormDto eventForm){
        Event updateEvent = eventRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("이벤트가 존재하지 않거나 삭제되었습니다.");
        });

        updateEvent.update(eventForm);
        return updateEvent.getId();
    }

    public void deleteEvent(Long id){
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("이벤트가 존재하지 않거나 삭제되었습니다.");
                });
        eventRepository.delete(event);
    }
}
