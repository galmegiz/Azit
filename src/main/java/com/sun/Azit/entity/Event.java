package com.sun.Azit.entity;

import com.sun.Azit.constant.Estatus;
import com.sun.Azit.dto.EventFormDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(name = "event")
@NoArgsConstructor
@Entity
public class Event extends BaseEntity{
    @Id @Column(name="event_id") @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Setter @Column(nullable = false) private String title;
    @Setter private String titleTag;
    @Setter @Column(nullable = false) private LocalDateTime recruitDeadline;
    @Setter @Column(nullable = false) private int fee;
    @Setter @Column(nullable = false) private int peopleLimit;
    @Setter @Column(nullable = false, length = 5000)  private String summary;
    @Setter @Column(nullable = false, length = 10000) private String content;
    @Setter @Column(nullable = false) @Enumerated(EnumType.STRING) private Estatus status;
    @Setter private String hashtag;
    @Setter @Column(nullable = false) private LocalDateTime startDate;
    @Setter @Column(nullable = false) private LocalDateTime endDate;

    @OneToOne(mappedBy = "event", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private EventImg eventImg;



    protected Event(String title, String titleTag, LocalDateTime recruitDeadline, int fee, int peopleLimit, String summary, String content, Estatus status, String hashtag, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.titleTag = titleTag;
        this.recruitDeadline = recruitDeadline;
        this.fee = fee;
        this.peopleLimit = peopleLimit;
        this.summary = summary;
        this.content = content;
        this.status = status;
        this.hashtag = hashtag;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Event of(String title, String titleTag, LocalDateTime recruitDeadline, int fee, int peopleLimit, String summary, String content, Estatus status, String hashtag, LocalDateTime start, LocalDateTime end) {
        return new Event(title, titleTag, recruitDeadline, fee, peopleLimit, summary, content, status, hashtag, start, end);
    }

    public EventFormDto toDto(){
        return new EventFormDto(this.id,
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
                this.endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id.equals(event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void update(EventFormDto eventForm) {
        this.title = eventForm.getTitle();
        this.titleTag = eventForm.getTitleTag();
        this.recruitDeadline = eventForm.getRecruitDeadline();
        this.fee = eventForm.getFee();
        this.peopleLimit = eventForm.getPeopleLimit();;
        this.summary = eventForm.getSummary();
        this.content = eventForm.getContent();
        this.status = eventForm.getStatus();
        this.hashtag = eventForm.getHashTag();
        this.startDate = eventForm.getStartDate();
        this.endDate = eventForm.getEndDate();
    }
}
