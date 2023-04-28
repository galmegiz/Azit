package com.sun.Azit.controller;

import com.sun.Azit.constant.Role;
import com.sun.Azit.dto.MemberFormDto;
import com.sun.Azit.dto.MemberLoginDto;
import com.sun.Azit.entity.Member;
import com.sun.Azit.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members/register")
    public String getMemberRegister(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberRegister";
    }

    @GetMapping("/members/login")
    public String getMemberLogin(Model model) {
        model.addAttribute("memberFormDto", new MemberLoginDto());
        return "member/memberLogin";
    }

    @PostMapping("/members/register")
    public String postMemberRegister(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "member/memberRegister";
        }

        try{
            Member newMember = Member.of(memberFormDto.getName(), memberFormDto.getEmail(), passwordEncoder.encode(memberFormDto.getPassword()), Role.USER);
            memberService.registerMember(newMember);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberRegister";
        }
        return "redirect:/";
    }

    @Transactional(readOnly = true)
    @GetMapping("/members/mypage/{memberEmail}")
    public String getMember(@PathVariable String memberEmail,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model){
        if(userDetails == null){
            return "/member/memberLogin";
        }
        String email = userDetails.getUsername();
        MemberFormDto memberFormDto = memberService.getMember(memberEmail, email);
        model.addAttribute("memberFormDto", memberFormDto);
        return "/member/mypage";
    }

    @PatchMapping("/members/mypage/{memberEmail}")
    @ResponseBody
    public boolean updateMember(@RequestBody MemberFormDto memberFormDto,
                               @AuthenticationPrincipal UserDetails userDetails){
        if(memberFormDto.getEmail() == null || !StringUtils.equals(memberFormDto.getEmail(), userDetails.getUsername())){
            return false;
        }

        memberService.updateMember(memberFormDto);
        return true;
    }

    @DeleteMapping("/members/mypage/{memberEmail}")
    @ResponseBody
    public String deleteMember(@PathVariable String memberEmail,
                                @AuthenticationPrincipal UserDetails userDetails){
        if(!StringUtils.equals(memberEmail, userDetails.getUsername())){
            return "정상로그인 후 이용바랍니다.";
        }

        memberService.deleteMember(memberEmail);
        SecurityContextHolder.clearContext();
        return "/";
    }

}
