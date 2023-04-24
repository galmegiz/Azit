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

    private final EventImgRepository eventImgRepository;
    private final FileService fileService;

    public void saveEventImg(Event event, MultipartFile eventImgFile) throws Exception{
        String oriImgName = eventImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(eventImgLocation, oriImgName, eventImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }
        EventImg eventImg = new EventImg();
        eventImg.setImgUrl(imgUrl);
        eventImg.setImgName(imgName);
        eventImg.setOriImgName(oriImgName);
        eventImg.setEvent(event);
        eventImgRepository.save(eventImg);
    }

}
