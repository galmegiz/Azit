package com.sun.Azit.service;

import com.sun.Azit.entity.Event;
import com.sun.Azit.entity.EventImg;
import com.sun.Azit.repository.EventImgRepository;
import com.sun.Azit.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.File;

@Service
@RequiredArgsConstructor
@Transactional
public class EventImgService {
    @Value("${eventImgLocation}")
    private String eventImgLocation;

    private final EventRepository eventRepository;
    private final EventImgRepository eventImgRepository;
    private final FileService fileService;

    public void saveEventImg(EventImg eventImg, MultipartFile eventImgFile, Long eventId) throws Exception{
        String oriImgName = eventImg.getOriImgName();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(eventImgLocation, oriImgName, eventImgFile.getBytes());
            imgUrl = File.separator + "images" + File.separator + "item" + File.separator + imgName;
        }
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            throw new EntityNotFoundException("이미지 업로드를 위한 게시글이 존재하지 않습니다.");
        });
        eventImg.of(oriImgName, imgName, imgUrl, event);
        eventImgRepository.save(eventImg);
    }
}
