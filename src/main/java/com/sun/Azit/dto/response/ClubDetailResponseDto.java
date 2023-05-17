package com.sun.Azit.dto.response;

import com.sun.Azit.constant.Cstatus;
import com.sun.Azit.dto.ClubFormDto;
import com.sun.Azit.dto.ImgDto;
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
public class ClubDetailResponseDto {
    private Long id;
    private String title;
    private String titleTag;
    private int peopleLimit;
    private String clubLeader;
    private String introduction;
    private String content;
    private Cstatus cstatus;
    private String hashTag;
    private List<ImgDto> clubImgList = new ArrayList<>();
    //private List<Long> imgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public static ClubDetailResponseDto fromDto(ClubFormDto clubFormDto){
        ClubDetailResponseDto clubDetailResponseDto = modelMapper.map(clubFormDto, ClubDetailResponseDto.class);
        clubDetailResponseDto.setClubLeader(clubFormDto.getClubLeader().getName());
        return clubDetailResponseDto;
    }
}
