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
        eventService.createEvent(nevent);
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
        eventService.createEvent(nevent);
        Assertions.assertThat(eventService.getEventDetail(1L)).isInstanceOf(EventFormDto.class);
        Assertions.assertThatThrownBy(() -> eventService.getEventDetail(2L))
            .isInstanceOf(IllegalStateException.class);

    }
}