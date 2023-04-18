package com.sun.Azit.dto;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.entity.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class EventDto{
    private String title;
    private String titleTag;
    private LocalDate recruitDeadline;
    private int fee;
    private int peopleLimit;
    private String summary;
    private String content;
    private Estatus status;
    private String hashtag;
    private LocalDate start;
    private LocalDate end;

    protected EventDto(String title, String titleTag, LocalDate recruitDeadline, int fee, int peopleLimit, String summary, String content, Estatus status, String hashtag, LocalDate start, LocalDate end) {
        this.title = title;
        this.titleTag = titleTag;
        this.recruitDeadline = recruitDeadline;
        this.fee = fee;
        this.peopleLimit = peopleLimit;
        this.summary = summary;
        this.content = content;
        this.status = status;
        this.hashtag = hashtag;
        this.start = start;
        this.end = end;
    }

    public static EventDto of(String title, String titleTag, LocalDate recruitDeadline, int fee, int peopleLimit, String summary, String content, Estatus status, String hashtag, LocalDate start, LocalDate end) {
        return new EventDto(title, titleTag, recruitDeadline, fee, peopleLimit, summary, content, status, hashtag, start, end);
    }

    public static EventDto from(Event entity){
        return new EventDto(
                entity.getTitle(),
                entity.getTitleTag(),
                entity.getRecruitDeadline(),
                entity.getFee(),
                entity.getPeopleLimit(),
                entity.getSummary(),
                entity.getContent(),
                entity.getStatus(),
                entity.getHashtag(),
                entity.getStart(),
                entity.getEnd()
        );
    }

    public Event toEntity(){
        return Event.of(title,
                titleTag,
                recruitDeadline,
                fee,
                peopleLimit,
                summary,
                content,
                status,
                hashtag,
                start,
                end);
    }

}
