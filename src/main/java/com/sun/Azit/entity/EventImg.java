package com.sun.Azit.entity;

import com.sun.Azit.dto.EventImgDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.Objects;

@Entity
@ToString
@Getter
@NoArgsConstructor
@Table(name = "item_img")
public class EventImg extends BaseEntity{
    @Id @Column(name="item_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName;
    private String oriImgName;
    private String imgUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    protected EventImg(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }

    // Dto <-> Entity
    private static ModelMapper modelMapper = new ModelMapper();

    public static EventImgDto toDto(EventImg eventImg){
        return modelMapper.map(eventImg, EventImgDto.class);
    }


    public void of(String oriImageName, String imgName, String imgUrl, Event event){
        this.oriImgName = oriImageName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.event = event;
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
}
