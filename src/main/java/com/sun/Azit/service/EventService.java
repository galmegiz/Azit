package com.sun.Azit.service;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.constant.SearchType;
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
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventImgService eventImgService;
    private final EventImgRepository eventImgRepository;

    public Page<EventFormDto> getEventLists(Pageable pageable){
        return eventRepository.findAll(pageable).map(event -> {
            EventFormDto eventDto = EventFormDto.from(event);
            EventImg eventImg = eventImgRepository.findByEvent(event)
                                                    .orElse(new EventImg("default", "default", "/images/event/default.PNG", event));
            EventImgDto eventImgDto = eventImg.toDto();
            eventDto.setEventImgDto(eventImgDto);
            return eventDto;
        });
    }

    public Page<EventFormDto> getEventLists(SearchType searchType, String searchValue, Pageable pageable) {
        if(StringUtils.isEmptyOrWhitespace(searchValue)){
            return getEventLists(pageable);
        }

        return switch (searchType){
            case TITLE -> eventRepository.findByTitleContaining(searchValue,pageable).map(EventFormDto::from);
            case FEE -> eventRepository.findByFeeLessThanEqual(Integer.parseInt(searchValue), pageable).map(EventFormDto::from);
            case STATUS -> eventRepository.findByStatus(searchValue,pageable).map(EventFormDto::from);
            case CONTENT -> eventRepository.findByContentContaining(searchType, pageable).map(EventFormDto::from);
            case PEOPLE_LIMIT -> eventRepository.findByPeopleLimitLessThanEqual(Integer.parseInt(searchValue),pageable).map(EventFormDto::from);
        };
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
        eventImgService.saveEventImg(newEvent, itemImg);
        eventRepository.save(newEvent);
        return newEvent;
    }

    public EventFormDto getEventDetail(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("이벤트가 존재하지 않거나 삭제되었습니다.");
        });
        EventImg eventImg = eventImgRepository.findByEvent(event).orElseThrow(() -> {
            throw new EntityNotFoundException("이미지가 존재하지 않습니다.");
        });
        EventFormDto eventFormDto = event.toDto();
        eventFormDto.setEventImgDto(eventImg.toDto());
        return eventFormDto;
    }

    public Long updateEvent(Long id, EventFormDto eventForm, MultipartFile multipartFile) throws Exception{
        Event updateEvent = eventRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("이벤트가 존재하지 않거나 삭제되었습니다.");
        });


        eventImgService.updateEventImg(eventForm.getEventImgDto(), multipartFile);
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
