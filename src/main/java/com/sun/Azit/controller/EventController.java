package com.sun.Azit.controller;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.dto.EventFormDto;
import com.sun.Azit.entity.Event;
import com.sun.Azit.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @Transactional(readOnly = true)
    @GetMapping("/events")
    public String getEventList(@PageableDefault(size = 6) Pageable pageable, Model model){
        Page<EventFormDto> events = eventService.getEventLists(pageable);

        int currentPage = events.getPageable().getPageNumber();
        int startPage = Math.max(currentPage - 4, 0);
        int endPage = Math.min(currentPage + 5, events.getTotalPages());
        model.addAttribute("events", events);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/event/eventList";
    }

    @GetMapping("/events/create")
    public String getEventForm(Model model){
        model.addAttribute("eventFormDto", new EventFormDto());
        return "/event/eventCreate";
    }

    @PostMapping("/events/create")
    public String createEvent(@Valid EventFormDto eventFormDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/event/eventCreate";
        }

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
        eventService.createEvent(newEvent);
        return "redirect:/";
    }

}
