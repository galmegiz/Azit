package com.sun.Azit.dto.request;


import com.sun.Azit.dto.EventFormDto;
import com.sun.Azit.dto.ImgDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class EventFormRequestDto {

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
    private String hashTag = "";
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @FutureOrPresent(message = "이벤트 시작일은 오늘 이후여야 합니다.")
    private LocalDateTime startDate;
    @FutureOrPresent(message = "이벤트 종료는 오늘 이후여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;



    protected EventFormRequestDto(String title, String titleTag, LocalDateTime recruitDeadLine, int fee, int peopleLimit, String summary, String content, String hashTag, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.titleTag = titleTag;
        this.recruitDeadline = recruitDeadLine;
        this.fee = fee;
        this.peopleLimit = peopleLimit;
        this.summary = summary;
        this.content = content;
        this.hashTag = hashTag;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static EventFormRequestDto from(EventFormDto eventFormDto){
        return new EventFormRequestDto(
                eventFormDto.getTitle(),
                eventFormDto.getTitleTag(),
                eventFormDto.getRecruitDeadline(),
                eventFormDto.getFee(),
                eventFormDto.getPeopleLimit(),
                eventFormDto.getSummary(),
                eventFormDto.getContent(),
                eventFormDto.getHashTag(),
                eventFormDto.getStartDate(),
                eventFormDto.getEndDate()
        );
    }

    public EventFormDto to(){
        return EventFormDto.of(this.title,
                this.titleTag,
                this.recruitDeadline,
                this.fee,
                this.peopleLimit,
                this.summary,
                this.content,
                this.hashTag,
                this.startDate,
                this.endDate);
    }
}
