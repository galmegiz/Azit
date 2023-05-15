package com.sun.Azit.dto.response;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.dto.EventFormDto;
import com.sun.Azit.dto.ImgDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@ToString
public class EventDetailResponseDto {

    private Long id;
    private String title;
    private String titleTag ;
    private LocalDateTime recruitDeadline;
    private int fee;
    private int peopleLimit;
    private String summary;
    private String content;
    private Estatus status;
    private String hashTag;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ImgDto eventImgDto;


    private EventDetailResponseDto(Long id, String title, String titleTag, LocalDateTime recruitDeadLine, int fee, int peopleLimit, String summary, String content, Estatus status, String hashTag, LocalDateTime startDate, LocalDateTime endDate, ImgDto eventImgDto) {
        this.id = id;
        this.title = title;
        this.titleTag = titleTag;
        this.recruitDeadline = recruitDeadLine;
        this.fee = fee;
        this.peopleLimit = peopleLimit;
        this.summary = summary;
        this.content = content;
        this.status = status;
        this.hashTag = hashTag;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventImgDto = eventImgDto;
    }


    public static EventDetailResponseDto from(EventFormDto eventFormDto){
        return new EventDetailResponseDto(eventFormDto.getId(),
                eventFormDto.getTitle(),
                eventFormDto.getTitleTag(),
                eventFormDto.getRecruitDeadline(),
                eventFormDto.getFee(),
                eventFormDto.getPeopleLimit(),
                eventFormDto.getSummary(),
                eventFormDto.getContent(),
                eventFormDto.getStatus(),
                eventFormDto.getHashTag(),
                eventFormDto.getStartDate(),
                eventFormDto.getEndDate(),
                eventFormDto.getEventImgDto());
    }

}
