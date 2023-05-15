package com.sun.Azit.controller;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.constant.Role;
import com.sun.Azit.constant.SearchType;
import com.sun.Azit.dto.EventFormDto;
import com.sun.Azit.dto.EventMemberDto;
import com.sun.Azit.dto.EventUpdateRequestDto;
import com.sun.Azit.dto.request.EventFormRequestDto;
import com.sun.Azit.dto.response.EventDetailResponseDto;
import com.sun.Azit.entity.Event;
import com.sun.Azit.entity.Member;
import com.sun.Azit.error.exception.AuthorityException;
import com.sun.Azit.error.validation.EventFormRequestValidator;
import com.sun.Azit.service.EventService;
import com.sun.Azit.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

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
        return "event/eventList";
    }

    @GetMapping("/events/{id}")
    public String getEventDetail(@PathVariable Long id, Model model){
        model.addAttribute("eventFormDto", EventDetailResponseDto.from(eventService.getEventDetail(id)));
        return "event/eventDetail";
    }


    @GetMapping("/events/apply")
    @ResponseBody
    public String applyEvent(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("eventId") Long eventId){
        if(userDetails == null){
            return "로그인이 필요한 서비스입니다.";
        }

        if(eventService.applyEvent(userDetails.getUsername(), eventId)){
            return "이벤트 신청을 완료했습니다.";
        }else{
            return "이미 등록된 사용자입니다.";
        }
    }

    @DeleteMapping("/events/cancel")
    @ResponseBody
    public String cancelEvent(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam("eventId") Long eventId){
        if(userDetails == null){
            return "로그인이 필요한 서비스입니다.";
        }
        if(eventService.cancelEvent(userDetails.getUsername(), eventId)){
            return "이벤트를 삭제했습니다.";
        }else{
            return "이벤트 삭제중 오류가 발생했습니다.";
        }

    }

    @Secured("ADMIN")
    @PatchMapping("/events/payment")
    @ResponseBody
    public String updatePayment(@RequestParam("memberId") Long memberId,
                                @RequestParam("eventId") Long eventId){

        eventService.updatePayment(memberId, eventId);
        return "지불 정보가 수정되었습니다";
    }

}
