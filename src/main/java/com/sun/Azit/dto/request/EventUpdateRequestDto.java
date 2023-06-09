package com.sun.Azit.dto;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.dto.request.EventFormRequestDto;
import com.sun.Azit.entity.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class EventUpdateRequestDto {

    @NotNull
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
    private String hashtag = "";
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @FutureOrPresent(message = "이벤트 시작일은 오늘 이후여야 합니다.")
    private LocalDateTime startDate;
    @FutureOrPresent(message = "이벤트 종료는 오늘 이후여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;
    private ImgDto eventImgDto;


    private EventUpdateRequestDto(Long id, String title, String titleTag, LocalDateTime recruitDeadLine, int fee, int peopleLimit, String summary, String content, Estatus status, String hashtag, LocalDateTime startDate, LocalDateTime endDate, ImgDto eventImgDto ) {
        this.id = id;
        this.title = title;
        this.titleTag = titleTag;
        this.recruitDeadline = recruitDeadLine;
        this.fee = fee;
        this.peopleLimit = peopleLimit;
        this.summary = summary;
        this.content = content;
        this.status = status;
        this.hashtag = hashtag;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventImgDto = eventImgDto;
    }

    public static EventUpdateRequestDto from(EventFormDto eventFormDto){
        return new EventUpdateRequestDto(
                eventFormDto.getId(),
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
                eventFormDto.getEventImgDto()
        );
    }

    public EventFormDto toDto(){
        return EventFormDto.of(this.id,
                this.title,
                this.titleTag,
                this.recruitDeadline,
                this.fee,
                this.peopleLimit,
                this.summary,
                this.content,
                this.status,
                this.hashtag,
                this.startDate,
                this.endDate,
                this.eventImgDto);
    }

}
