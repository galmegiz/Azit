package com.sun.Azit.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class ClubImg {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_img_id")
    private long id;

    private String imgName;
    private String oriImgName;
    private String imgUrl;

    @Builder
    public ClubImg(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
