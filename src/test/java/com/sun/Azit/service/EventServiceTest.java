/*
package com.sun.Azit.service;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.dto.EventFormDto;
import com.sun.Azit.entity.Event;
import com.sun.Azit.repository.EventRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class EventServiceTest {
    @Autowired
    EventService eventService;
    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("이벤트 생성 테스트")
    void createEventTest() {
        Event nevent = Event.of("title",
                "titleTag",
                LocalDateTime.now(),
                100,
                100,
                "summary",
                "content",
                Estatus.OPEN,
                "hashtag",
                LocalDateTime.now(),
                LocalDateTime.now());
        EventFormDto eventForm = nevent.toDto();
        eventService.createEvent(eventForm);
        Event gevent = eventRepository.findByTitle("title").get();
        Assertions.assertThat(gevent.getTitle()).isEqualTo(nevent.getTitle());
    }

    @Test
    @DisplayName("이벤트 디테일 검색 테스트")
    void getEventDetailTest(){
        Event nevent = Event.of("title",
                "titleTag",
                LocalDateTime.now(),
                100,
                100,
                "summary",
                "content",
                Estatus.OPEN,
                "hashtag",
                LocalDateTime.now(),
                LocalDateTime.now());
        EventFormDto eventForm = nevent.toDto();
        eventService.createEvent(eventForm);
        System.out.println(eventRepository.findByTitle("title").get());
        Assertions.assertThat(eventService.getEventDetail(1L)).isInstanceOf(EventFormDto.class);
        Assertions.assertThatThrownBy(() -> eventService.getEventDetail(2L))
            .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    @DisplayName("이벤트 업데이트 테스트")
    void eventUpdateTest(){
        Event nevent1 = Event.of("title1",
                "titleTag",
                LocalDateTime.now(),
                100,
                100,
                "summary",
                "content",
                Estatus.OPEN,
                "hashtag",
                LocalDateTime.now(),
                LocalDateTime.now());

        Event nevent2 = Event.of("title2",
                "titleTag",
                LocalDateTime.now(),
                100,
                100,
                "summary",
                "content",
                Estatus.OPEN,
                "hashtag",
                LocalDateTime.now(),
                LocalDateTime.now());
        eventService.createEvent(nevent1.toDto());
        eventService.createEvent(nevent2.toDto());
        System.out.println(eventRepository.findByTitle("title1"));
        System.out.println(eventRepository.findByTitle("title2"));
        Event event = eventRepository.findById(3L).get();
        String title = event.getTitle();
        System.out.println(title);
        EventFormDto eventFormDto = event.toDto();
        String newTitle = "안녕하세요";
        eventFormDto.setTitle(newTitle);
        eventService.updateEvent(3L, eventFormDto);
        Assertions.assertThat(eventRepository.findById(3L).get().getTitle()).isEqualTo(newTitle);
    }
}*/
