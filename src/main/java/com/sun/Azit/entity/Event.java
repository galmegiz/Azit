package com.sun.Azit.entity;

import com.sun.Azit.constant.Estatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(name = "event")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Setter @Column(nullable = false) private String title;
    @Setter @Column() private String titleTag;


    @Setter @Column(nullable = false) private LocalDate recruitDeadline;
    @Setter @Column(nullable = false) private int fee;
    @Setter @Column(nullable = false) private int peopleLimit;

    @Setter @Column(nullable = false, length = 5000)  private String summary;
    @Setter @Column(nullable = false, length = 10000) private String content;
    @Setter @Column(nullable = false) @Enumerated(EnumType.STRING) private Estatus status;
    @Setter private String hashtag;
    @Setter @Column(nullable = false) private LocalDate start;
    @Setter @Column(nullable = false) private LocalDate end;

    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;
    @CreatedDate @Column(nullable = false, length = 100) private LocalDateTime createdAt;
    @CreatedBy @Column(nullable = false) private String createdBy;
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy;


    protected Event(String title, String titleTag, LocalDate recruitDeadline, int fee, int peopleLimit, String summary, String content, Estatus status, String hashtag, LocalDate start, LocalDate end) {
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

    public static Event of(String title, String titleTag, LocalDate recruitDeadline, int fee, int peopleLimit, String summary, String content, Estatus status, String hashtag, LocalDate start, LocalDate end) {
        return new Event(title, titleTag, recruitDeadline, fee, peopleLimit, summary, content, status, hashtag, start, end);
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
}
