package com.sun.Azit.controller;

import com.sun.Azit.constant.SearchType;
import com.sun.Azit.dto.EventDto;
import com.sun.Azit.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public String events(
            @RequestParam(required = false)SearchType searchType,
            @RequestParam(required = false)String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable,
            ModelMap model){
        Page<EventDto> events = eventService.searchEvent(searchType, searchValue, pageable);
        model.addAttribute("events", events);
        model.addAttribute("searchType", SearchType.values());
        return "event/index";
    }
    @PostMapping("/register")
    public void registerEvent(EventDto eventDto){
        eventService.saveEvent(eventDto);

    }

    @GetMapping("/{eventId}")
    public String getEvent(@PathVariable("eventId") Long eventId, ModelMap map){
        EventDto eventDto = eventService.getEvent(eventId);
        map.addAttribute("event", eventDto);
        return "event/detail";
    }

    @PostMapping("/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") Long eventId){
        eventService.deleteEvent(eventId);
        return "redirect:/event";
    }
}
