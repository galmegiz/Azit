package com.sun.Azit.entity;


import com.sun.Azit.dto.ImgDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.Objects;

@Entity
@ToString
@Getter
@NoArgsConstructor
@Table(name = "event_img")
public class EventImg extends BaseEntity{
    @Id @Column(name="event_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter private String imgName;
    @Setter private String oriImgName;
    @Setter private String imgUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @Setter
    private Event event;

    protected EventImg(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }

    public EventImg(String imgName, String oriImgName, String imgUrl, Event event) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.event = event;
    }

    // Dto <-> Entity
    private static ModelMapper modelMapper = new ModelMapper();

    public ImgDto toDto(){
        return modelMapper.map(this, ImgDto.class);
    }


    public static EventImg of(String oriImageName, String imgName, String imgUrl){
        return new EventImg(
                oriImageName,
                imgName,
                imgUrl
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventImg eventImg = (EventImg) o;
        return id.equals(eventImg.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



    public void update(String imgName, String imgUrl, String oriImgName) {
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.oriImgName = oriImgName;
    }
}
