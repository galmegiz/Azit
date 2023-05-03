package com.sun.Azit.controller;

import com.sun.Azit.dto.ClubFormDto;
import com.sun.Azit.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @GetMapping("/club")
    @Transactional(readOnly = true)
    public String getClubList(@PageableDefault Pageable pageableQ, Model model){
        Pageable pageable = PageRequest.of(pageableQ.getPageNumber() > 0 ? pageableQ.getPageNumber() : 0, 6);
        Page<ClubFormDto> clubs = clubService.getClubList(pageable);

        int currentPage = clubs.getPageable().getPageNumber();
        int startPage = Math.max(currentPage - 2, 0);
        int endPage;
        if(clubs.getTotalPages() - 1 <= 4){
            endPage = clubs.getTotalPages() - 1;
        }else{
            endPage = (currentPage + 2 <= 4) ? 4 : Math.min(currentPage + 2, clubs.getTotalPages());
        }
        model.addAttribute("clubs", clubs);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "club/clubList";
    }

    @GetMapping("/club/{id}")
    @Transactional(readOnly = true)
    public String getClubDetail(@PathVariable("id") long id, Model model){
        try{
            ClubFormDto clubFormDto = clubService.getClubDetail(id);
            System.out.println(StringUtils.isEmpty(clubFormDto.getClubImgList().get(1).getImgUrl()));
            model.addAttribute("clubFormDto", clubFormDto);
        }catch(Exception e){
            model.addAttribute("errorMessage", e);
            return "redirect:/";
        }
        return "club/clubDetail";
    }
    @GetMapping("/club/form")
    @Transactional(readOnly = true)
    public String getClubDetail(Model model){
        model.addAttribute("clubFormDto", new ClubFormDto());
        return "club/clubCreate";
    }

    @PostMapping("/club/form")
    public String createClub(@Valid ClubFormDto clubFormDto,
                             BindingResult bindingResult,
                             Model model,
                             @RequestParam("imgFile")List<MultipartFile> imgList,
                             @AuthenticationPrincipal UserDetails userDetails){
        System.out.println(imgList.get(0).getOriginalFilename());

        if(bindingResult.hasErrors()){
            return "club/clubCreate";
        }

        if(imgList.get(0).isEmpty() && clubFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 이미지는 필수입니다.");
            return "club/clubCreate";
        }

        if(userDetails == null){
            model.addAttribute("errorMessage", "로그인이 필요한 서비스입니다.");
            return "/";
        }
        try{
            clubService.createClub(clubFormDto, userDetails.getUsername(), imgList);
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
            return "club/clubCreate";
        }

        return "redirect:/club";
    }
}
