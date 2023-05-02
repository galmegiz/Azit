package com.sun.Azit.entity;

import com.sun.Azit.constant.Cstatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@NoArgsConstructor
@Table(name = "club")
public class Club extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    Long id;

    @Setter @Column(nullable = false) private String title;

    @Setter private String titleTag;

    @Setter @Column(nullable = false) private int peopleLimit;
    @Setter @Column(nullable = false) @Lob private String introduction;
    @Setter @Column(nullable = false) @Lob private String content;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private Cstatus cStatus;
    @Setter private String hashTag;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    @Setter private Member clubLeader;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ClubImg> clubImgList = new ArrayList<>();

}
