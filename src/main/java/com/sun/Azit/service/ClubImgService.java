package com.sun.Azit.service;

import com.sun.Azit.entity.Club;
import com.sun.Azit.entity.ClubImg;
import com.sun.Azit.repository.ClubImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubImgService {
    @Value("${clubImgLocation}")
    private String clubImgLocation;

    private final ClubImgRepository clubImgRepository;
    private final FileService fileService;
    public void save(ClubImg newImg, MultipartFile multipartFile) throws Exception{
        String oriImgName = multipartFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(clubImgLocation, oriImgName, multipartFile.getBytes());
            imgUrl = "/images/club/" + imgName;
        }

        newImg.update(imgName, oriImgName, imgUrl);
        clubImgRepository.save(newImg);
    }

    public List<ClubImg> getClubImg(Club club){
        return clubImgRepository.findByClub(club).orElseThrow(() -> {
                throw new EntityNotFoundException("해당 클럽 이미지가 존재하지 않습니다.");
            }
        );
    }
}
