package com.sun.Azit.service;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.constant.PaymentStatus;
import com.sun.Azit.constant.Role;
import com.sun.Azit.constant.SearchType;
import com.sun.Azit.dto.EventFormDto;

import com.sun.Azit.dto.EventMemberDto;
import com.sun.Azit.dto.ImgDto;
import com.sun.Azit.entity.Event;
import com.sun.Azit.entity.EventImg;
import com.sun.Azit.entity.EventMember;
import com.sun.Azit.entity.Member;
import com.sun.Azit.error.exception.AuthorityException;
import com.sun.Azit.repository.EventImgRepository;
import com.sun.Azit.repository.EventMemberRepository;
import com.sun.Azit.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Transactional
@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventImgService eventImgService;
    private final EventImgRepository eventImgRepository;
    private final MemberService memberService;
    private final EventMemberRepository eventMemberRepository;

    @Transactional(readOnly = true)
    public Page<EventFormDto> getEventLists(Pageable pageable){
        return eventRepository.findAll(pageable).map(event -> {
            EventFormDto eventDto = EventFormDto.from(event);
            EventImg eventImg = eventImgRepository.findByEvent(event)
                                                    .orElse(new EventImg("default", "default", "/images/event/default.PNG", event));
            ImgDto imgDto = eventImg.toDto();
            eventDto.setEventImgDto(imgDto);
            return eventDto;
        });
    }
    @Transactional(readOnly = true)
    public Page<EventFormDto> getEventLists(SearchType searchType, String searchValue, Pageable pageable) {
        if(StringUtils.isEmptyOrWhitespace(searchValue)){
            return getEventLists(pageable);
        }

        return switch (searchType){
            case TITLE -> eventRepository.findByTitleContaining(searchValue,pageable).map(EventFormDto::from);
            case FEE -> eventRepository.findByFeeLessThanEqual(Integer.parseInt(searchValue), pageable).map(EventFormDto::from);
            case STATUS -> eventRepository.findByStatus(Estatus.valueOf(searchValue),pageable).map(EventFormDto::from);
            case CONTENT -> eventRepository.findByContentContaining(searchType, pageable).map(EventFormDto::from);
            case PEOPLE_LIMIT -> eventRepository.findByPeopleLimitLessThanEqual(Integer.parseInt(searchValue),pageable).map(EventFormDto::from);
        };
    }


    public Event createEvent(EventFormDto eventFormDto, MultipartFile itemImg) throws IOException {
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
    @Transactional(readOnly = true)
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

    public Long updateEvent(EventFormDto eventForm, MultipartFile multipartFile) throws Exception {
        Event updateEvent = eventRepository.findById(eventForm.getId())
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


    public boolean applyEvent(String email, Long eventId) {
        Member member = memberService.findMember(email);
        if(isPresentEventMember(member, eventId)){
            return false;
        }

        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            throw new EntityNotFoundException("이벤트가 존재하지 않거나 삭제되었습니다.");
        });

        EventMember eventMember = EventMember.builder()
                .memberRole(member.getRole())
                .event(event)
                .paymentStatus(PaymentStatus.UNPAID)
                .member(member).build();
        eventMemberRepository.save(eventMember);

        return true;
    }
    @Transactional(readOnly = true)
    public boolean isPresentEventMember(Member member, Long eventId){
        return eventMemberRepository.findByMemberAndEventId(member, eventId).isPresent();
    }
    @Transactional(readOnly = true)
    public Page<EventMemberDto> getEventMemberList(Long id, Pageable pageable) {
        Event event = eventRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("이벤트가 존재하지 않습니다.");
        });
        return eventMemberRepository.findByEvent(event,pageable).map(EventMemberDto::from);
    }

    public boolean cancelEvent(String email, Long eventId){
        Member member = memberService.findMember(email);
        if(isPresentEventMember(member, eventId) || member.getRole() == Role.ADMIN){
            eventMemberRepository.deleteByMemberIdAndEventId(member.getId(), eventId);
            return true;
        }else{
            throw new AuthorityException("삭제");
        }
    }

    public void updatePayment(Long memberId, Long eventId){
        EventMember eventMember = eventMemberRepository
                .findByMemberIdAndEventId(memberId, eventId)
                .orElseThrow(() -> {throw new EntityNotFoundException("해당 회원은 이벤트를 신청하지 않았습니다.");});
        eventMember.setPaymentStatus(PaymentStatus.COMPLETE_PAYMENT);
    }
}
