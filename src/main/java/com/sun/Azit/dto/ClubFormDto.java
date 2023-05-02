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
    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;
    private String titleTag;
    @Max(value = 100, message = "최대 100명을 넘을 수 없습니다.")
    @Min(value = 2, message = "최소 2명 이상입니다.")
    private int peopleLimit;
    private Member clubLeader;
    @NotBlank(message = "소개를 입력해주세요")
    private String introduction;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;
    private Cstatus cstatus;
    private String hashTag;
    private List<ImgDto> imgDtoList = new ArrayList<>();
    //private List<Long> imgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Club toEntity(){
        return modelMapper.map(this, Club.class);
    }

    public static ClubFormDto fromEntity(Club club){
        return modelMapper.map(club, ClubFormDto.class);
    }
}
