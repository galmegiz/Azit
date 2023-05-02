package com.sun.Azit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@NoArgsConstructor
public class ClubImgDto {
    private Long id;
    private String oriImgName;
    private String imgUrl;
    private static ModelMapper modelMapper = new ModelMapper();

    public static <T, U> U from(T source, Class<U> destinationType){
        return modelMapper.map(source, destinationType);
    }
}
