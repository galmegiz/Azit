package com.sun.Azit.entity;

import com.sun.Azit.constant.Cstatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Getter
@NoArgsConstructor
public class Club extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    Long id;

    @Setter @Column(nullable = false) private String title;

    @Setter private String titleTag;

    @Setter @Column(nullable = false) private int peopleLimit;
    @Setter @Column(nullable = false) @Lob private String introduction;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private Cstatus cStatus;
    @Setter private String hashTag;

    @OneToOne
    @JoinColumn(name = "member_id")
    @Setter @Column(nullable = false) private Member clubLeader;

}
