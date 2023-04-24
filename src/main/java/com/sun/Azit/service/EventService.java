package com.sun.Azit.service;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.dto.EventFormDto;
import com.sun.Azit.dto.EventImgDto;
import com.sun.Azit.entity.Event;
import com.sun.Azit.entity.EventImg;
import com.sun.Azit.repository.EventImgRepository;
import com.sun.Azit.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventImgService eventImgService;
    private final EventImgRepository eventImgRepository;

    public Page<EventFormDto> getEventLists(Pageable pageable){
        //return eventRepository.findAll(pageable).map(EventFormDto::from);
        //return eventRepository.findAll(pageable).map()
        //eventRepository.findAll(pageable).map(even)

        return eventRepository.findAll(pageable).map(event -> {
            EventFormDto eventDto = EventFormDto.from(event);
            EventImg eventImg = eventImgRepository.findByEvent(event).orElse(new EventImg("aa", "aa", "aa", event));
            EventImgDto eventImgDto = eventImg.toDto();
            eventDto.setEventImgDto(eventImgDto);
            return eventDto;
        });

/*        return eventRepository.findAll(pageable).map(new Function<Event, EventFormDto>() {
            @Override
            public EventFormDto converter(Event event) {
                EventFormDto eventDto = EventFormDto.from(event);
                EventImg eventImg = eventImgRepository.findByEvent(event).orElse(new EventImg("aa", "aa", "aa", event));
                EventImgDto eventImgDto = eventImg.toDto();
                eventDto.setEventImgDto(eventImgDto);
                return eventDto;
            }
        });*/
    }
    public Event createEvent(EventFormDto eventFormDto, MultipartFile itemImg) throws Exception{
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
        eventRepository.save(newEvent);


        EventImg eventImg = new EventImg();
        eventImg.setEvent(newEvent);

        eventImgService.saveEventImg(eventImg, itemImg);
        return newEvent;
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
