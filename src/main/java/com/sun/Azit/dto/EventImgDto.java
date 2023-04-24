package com.sun.Azit.dto;

import com.sun.Azit.entity.Event;
import com.sun.Azit.entity.EventImg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@NoArgsConstructor
public class EventImgDto {
    private Long id;
    private String oriImgName;
    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static EventImgDto from(EventImg eventImg){
        return modelMapper.map(eventImg, EventImgDto.class);
    }

}
