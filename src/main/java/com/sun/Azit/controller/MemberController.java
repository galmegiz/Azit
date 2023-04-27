package com.sun.Azit.controller;

import com.sun.Azit.constant.Role;
import com.sun.Azit.dto.MemberFormDto;
import com.sun.Azit.dto.MemberLoginDto;
import com.sun.Azit.entity.Member;
import com.sun.Azit.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
