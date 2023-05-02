package com.sun.Azit.dto;

import com.sun.Azit.constant.Cstatus;
import com.sun.Azit.entity.Member;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ClubFormDto {
    Long id;
    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;
    private String titleTag;
    @Max(value = 100, message = "최대 100명을 넘을 수 없습니다.")
    @Min(value = 2, message = "최소 2명 이상입니다.")
    private int peopleLimit;
    @NotBlank(message = "소개를 입력해주세요")
    private String introduction;
    private Cstatus cstatus;
    private String hashTag;
    private ClubImgDto clubImgDto;
}
