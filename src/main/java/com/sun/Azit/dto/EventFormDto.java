package com.sun.Azit.dto;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.entity.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class EventFormDto {

    private Long id;
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;
    private String titleTag = "";
    @FutureOrPresent(message = "모집 마감일은 오늘 이후여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime recruitDeadline;
    @Max(value = 100, message = "100만 원을 넘을 수 없습니다.")
    @Min(value = 1, message = "최소 금액을 입력해야 합니다.")
    private int fee;
    @Max(value = 100, message = "최대 100명을 넘을 수 없습니다.")
    @Min(value = 1, message = "최소 인원을 입력해야 합니다.")
    private int peopleLimit;
    private String summary = "";
    @NotBlank(message = "내용을 입력해주세요")
    private String content;
    private Estatus status = Estatus.OPEN_SOON;
    private String hashTag = "";
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @FutureOrPresent(message = "이벤트 시작일은 오늘 이후여야 합니다.")
    private LocalDateTime startDate;
    @FutureOrPresent(message = "이벤트 종료는 오늘 이후여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;
    private EventImgDto eventImgDto;


    public EventFormDto(Long id, String title, String titleTag, LocalDateTime recruitDeadLine, int fee, int peopleLimit, String summary, String content, Estatus status, String hashTag, LocalDateTime startDate, LocalDateTime endDate) {
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
    }

    public static EventFormDto from(Event event){
       return new EventFormDto(event.getId(),
               event.getTitle(),
               event.getTitleTag(),
               event.getRecruitDeadline(),
               event.getFee(),
               event.getPeopleLimit(),
               event.getSummary(),
               event.getContent(),
               event.getStatus(),
               event.getHashtag(),
               event.getStartDate(),
               event.getEndDate());
    }

}
