package com.sun.Azit.service;

import com.sun.Azit.constant.Cstatus;
import com.sun.Azit.dto.ClubFormDto;
import com.sun.Azit.dto.ClubImgDto;
import com.sun.Azit.dto.ImgDto;
import com.sun.Azit.entity.Club;
import com.sun.Azit.entity.ClubImg;
import com.sun.Azit.repository.ClubRepository;
import com.sun.Azit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubService {

    private final MemberService memberService;
    private final ClubRepository clubRepository;
    private final ClubImgService clubImgService;

    public Club createClub(ClubFormDto clubFormDto, String email, List<MultipartFile> clubImg) throws Exception{
        Club newClub = clubFormDto.toEntity();
        newClub.setCStatus(Cstatus.WAITING);
        newClub.setClubLeader(memberService.findMember(email));
        clubRepository.save(newClub);

        for(int i = 0; i < clubImg.size(); i++){
            ClubImg newImg = new ClubImg();
            newImg.setClub(newClub);
            newImg.setRepImgYn(i == 0 ? "Y" : "N");
            clubImgService.save(newImg, clubImg.get(i));
        }

        return newClub;
    }


    public ClubFormDto getClubDetail(long id) {
        Club club = clubRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("해당 클럽이 존재하지 않습니다.");
        });

        List<ClubImg> clubImgList = clubImgService.getClubImg(club);
        List<ImgDto> clubImgDtoList = new ArrayList<>();
        for (ClubImg clubImg : clubImgList) {
             ImgDto imgDto = ImgDto.from(clubImg, ImgDto.class);
             clubImgDtoList.add(imgDto);
        }

        ClubFormDto clubFormDto = ClubFormDto.fromEntity(club);
        clubFormDto.setImgDtoList(clubImgDtoList);
        return clubFormDto;
    }
}
