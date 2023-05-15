package com.sun.Azit.controller.admin;

import com.sun.Azit.constant.SearchType;
import com.sun.Azit.dto.EventFormDto;
import com.sun.Azit.dto.EventMemberDto;
import com.sun.Azit.dto.EventUpdateRequestDto;
import com.sun.Azit.dto.request.EventFormRequestDto;
import com.sun.Azit.error.exception.WrongIdException;
import com.sun.Azit.service.EventService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;
    @Transactional(readOnly = true)
    @GetMapping("")
    public String getAdminEventList(
            @RequestParam(required = false) SearchType searchType,
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
        return "event/admin/eventList";
    }
    @GetMapping("/form")
    public String getEventForm(Model model){
        model.addAttribute("eventFormDto", new EventFormRequestDto());
        return "event/admin/eventCreate";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/form")
    public String createEvent(@Validated EventFormRequestDto eventFormRequestDto,
                              BindingResult bindingResult,
                              @RequestParam("eventImgFile") MultipartFile eventImgFile) throws IOException {
        if (bindingResult.hasErrors()) {
            return "event/admin/eventCreate";
        }
        eventService.createEvent(eventFormRequestDto.to(), eventImgFile);
        return "redirect:/admin/events";
    }

    @GetMapping("/{id}/form")
    public String getEventUpdateForm(@PathVariable Long id, Model model){
        if(id == null){
            log.warn("이벤트 수정 비정상적 접근");
            throw new WrongIdException("정상적인 접근이 아닙니다.");
        }
        EventFormDto eventFormDto = eventService.getEventDetail(id);
        model.addAttribute("eventFormDto", EventUpdateRequestDto.from(eventFormDto));
        return "event/admin/eventUpdate";
    }

    @PostMapping("/{id}")
    public String updateEvent(@Validated @ModelAttribute("eventFormDto") EventUpdateRequestDto eventUpdateRequestDto,
                              BindingResult bindingResult,
                              @RequestParam("eventImgFile") MultipartFile eventImgFile) throws Exception {

        if(bindingResult.hasErrors()){
            if(bindingResult.getFieldError("id") != null){
                log.warn("이벤트 업데이트 비정상적 접근");
                throw new WrongIdException("정상적인 접근이 아닙니다.");
            }
            return "event/admin/eventCreate";
        }

        Long eventId = eventService.updateEvent(eventUpdateRequestDto.toDto(), eventImgFile);
        return "redirect:/events/" + eventId;
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public String searchEvent(@PathVariable Long id,
                              @PageableDefault Pageable pageableQ,
                              Model model){
        Pageable pageable = PageRequest.of(pageableQ.getPageNumber() >= 0 ? pageableQ.getPageNumber() : 0, 10, Sort.by("createdAt").descending());
        EventFormDto eventFormDto = eventService.getEventDetail(id);
        Page<EventMemberDto> eventMemberDto = eventService.getEventMemberList(id, pageable);
        int currentPage = eventMemberDto.getPageable().getPageNumber();
        int startPage = Math.max(currentPage - 2, 0);
        int endPage;
        if (eventMemberDto.getTotalPages() - 1 <= 4){
            endPage = eventMemberDto.getTotalPages() - 1;
        } else{
            endPage = (currentPage + 2 <= 4) ? 4 : Math.min(currentPage + 2, eventMemberDto.getTotalPages() - 1);
        }
        model.addAttribute("eventMemberDto", eventMemberDto);
        model.addAttribute("eventFormDto", eventFormDto);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "event/admin/eventDetail";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteEvent(@PathVariable Long id){
        if(id == null) {
            log.warn("이벤트 삭제 비정상적 접근");
            throw new WrongIdException("정상적인 접근이 아닙니다.");
        }
        eventService.deleteEvent(id);
        return id + "가 성공적으로 제거되었습니다.";
    }
}
