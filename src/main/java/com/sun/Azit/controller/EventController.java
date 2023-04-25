package com.sun.Azit.controller;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.constant.SearchType;
import com.sun.Azit.dto.EventFormDto;
import com.sun.Azit.entity.Event;
import com.sun.Azit.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @Transactional(readOnly = true)
    @GetMapping("/events")
    public String getEventList(@PageableDefault Pageable pageableQ, Model model){
        Pageable pageable = PageRequest.of(pageableQ.getPageNumber() >= 0 ? pageableQ.getPageNumber() : 0, 6);
        Page<EventFormDto> events = eventService.getEventLists(pageable);

        int currentPage = events.getPageable().getPageNumber();
        int startPage = Math.max(currentPage - 2, 0);
        int endPage;
        if (events.getTotalPages() - 1 <= 4){
            endPage = events.getTotalPages() - 1;
        } else{
            endPage = (currentPage + 2 <= 4) ? 4 : Math.min(currentPage + 2, events.getTotalPages() - 1);
        }

        model.addAttribute("events", events);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/event/eventList";
    }

    @Transactional(readOnly = true)
    @GetMapping("/events/{id}")
    public String getEventDetail(@PathVariable Long id, Model model){
        try{
            EventFormDto eventFormDto = eventService.getEventDetail(id);
            model.addAttribute("eventFormDto", eventFormDto);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "/event/eventList";
        }
        return "/event/eventDetail";
    }

    @Transactional(readOnly = true)
    @GetMapping("/admin/events")
    public String getAdminEventList(
            @RequestParam(required = false)SearchType searchType,
            @RequestParam(required = false)String searchValue,
            @PageableDefault Pageable pageableQ, Model model){
        Pageable pageable = PageRequest.of(pageableQ.getPageNumber() >= 0 ? pageableQ.getPageNumber() : 0, 10, Sort.by("createdAt").descending());
        Page<EventFormDto> events = eventService.getEventLists(searchType, searchValue, pageable);

        int currentPage = events.getPageable().getPageNumber();
        int startPage = Math.max(currentPage - 2, 0);
        int endPage;
        if (events.getTotalPages() - 1 <= 4){
            endPage = events.getTotalPages() - 1;
        } else{
            endPage = (currentPage + 2 <= 4) ? 4 : Math.min(currentPage + 2, events.getTotalPages() - 1);
        }
        model.addAttribute("events", events);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/event/admin/eventList";
    }

    @GetMapping("/admin/events/form")
    public String getEventForm(Model model){
        model.addAttribute("eventFormDto", new EventFormDto());
        return "/event/admin/eventCreate";
    }

    @PostMapping("/admin/events/form")
    public String createEvent(@Valid EventFormDto eventFormDto,
                              BindingResult bindingResult, Model model,
                              @RequestParam("eventImgFile")MultipartFile eventImgFile) {
        if (bindingResult.hasErrors()) {
            return "/event/admin/eventCreate";
        }
        try{
            eventService.createEvent(eventFormDto, eventImgFile);
        } catch (Exception e){
            model.addAttribute("error Message", "상품 등록 중 에러가 발생했습니다.");
            return "/event/admin/eventCreate";
        }

        return "redirect:/admin/events";
    }

    @Transactional(readOnly = true)
    @GetMapping("/admin/events/{id}/form")
    public String getUpdateEventForm(@PathVariable Long id, Model model){
        if(id == null){
            model.addAttribute("errorMessage", "게시글 번호를 입력해주세요");
            return "/event/admin/eventList";
        }

        try{
            EventFormDto eventFormDto = eventService.getEventDetail(id);
            model.addAttribute("eventFormDto", eventFormDto);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "/event/admin/eventList";
        }
        return "/event/admin/eventCreate";
    }

    @PostMapping("/admin/events/{id}")
    public String updateEvent(@PathVariable Long id, @Valid EventFormDto eventFormDto,
                              BindingResult bindingResult, Model model,
                              @RequestParam("eventImgFile") MultipartFile eventImgFile){
        if(id == null){
            model.addAttribute("errorMessage", "게시글 번호를 입력해주세요");
            return "/event/admin/eventList";
        }
        if(bindingResult.hasErrors()){
            return "/event/admin/eventCreate";
        }

        Long updatedEventId = 0L;
        try{
            updatedEventId = eventService.updateEvent(id, eventFormDto, eventImgFile);
        }catch (Exception e){
            model.addAttribute("errorMessage", "이벤트 수정 중 에러가 발생하였습니다.");
            return "redirect:/events";
        }
        return "redirect:/events/" + updatedEventId;

    }

    @DeleteMapping("/admin/events/{id}")
    @ResponseBody
    public String deleteEvent(@PathVariable Long id, Model model){
        if(id == null) {
            model.addAttribute("errorMessage", "게시글 번호를 입력해주세요");
            return "/event/admin/eventList";
        }
        eventService.deleteEvent(id);
        return id.toString();
    }

}
