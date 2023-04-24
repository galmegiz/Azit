package com.sun.Azit.repository;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.entity.Event;
import com.sun.Azit.entity.EventImg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")

class EventImgRepositoryTest {

    @Autowired EventImgRepository eventImgRepository;
    @Autowired EventRepository eventRepository;

    @Test
    void imgsearch(){
        Event event = Event.of("111",
                "111",
                LocalDateTime.now(),
                20,
                20,
                "ddd",
                "ddd",
                Estatus.OPEN_SOON,
                "dd",
                LocalDateTime.now(),
                LocalDateTime.now());
        Event event2 = Event.of("111",
                "111",
                LocalDateTime.now(),
                20,
                20,
                "ddd",
                "ddd",
                Estatus.OPEN_SOON,
                "dd",
                LocalDateTime.now(),
                LocalDateTime.now());
        EventImg eventImg = EventImg.of("ddd","ddd","ddd");
        eventImg.setEvent(event);
        eventRepository.save(event);
        eventRepository.save(event2);
        eventImgRepository.save(eventImg);
        EventImg e1 = eventImgRepository.findByEvent(event);
        System.out.println(e1);
        System.out.println(eventImg);



    }



}