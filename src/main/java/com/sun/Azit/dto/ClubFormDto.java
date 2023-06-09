package com.sun.Azit.dto;

import com.sun.Azit.constant.Cstatus;
import com.sun.Azit.entity.Club;
import com.sun.Azit.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ClubFormDto {
    private Long id;
    private String title;
    private String titleTag;
    private int peopleLimit;
    private Member clubLeader;
    private String introduction;
    private String content;
    private Cstatus cstatus;
    private String hashTag;
    private List<ImgDto> clubImgList = new ArrayList<>();
    //private List<Long> imgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();



    public Club toEntity(){
        return modelMapper.map(this, Club.class);
    }

    public static ClubFormDto fromEntity(Club club){
        return modelMapper.map(club, ClubFormDto.class);
    }

    public static ClubFormDto fromEntityAndImg(Club club, List<ImgDto> imgDtos) {
        ClubFormDto clubFormDto = modelMapper.map(club, ClubFormDto.class);
        clubFormDto.setClubImgList(imgDtos);
        return clubFormDto;
    }
}
