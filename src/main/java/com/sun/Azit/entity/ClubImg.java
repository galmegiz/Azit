package com.sun.Azit.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "club_img")
@Getter
public class ClubImg {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_img_id")
    private long id;

    @Setter private String imgName;
    @Setter private String oriImgName;
    @Setter private String imgUrl;

    @Setter private String repImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @Setter
    private Club club;

    @Builder
    public ClubImg(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }

    public void update(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
