package com.sun.Azit.service;

import com.sun.Azit.constant.Role;
import com.sun.Azit.dto.MemberFormDto;
import com.sun.Azit.entity.Member;
import com.sun.Azit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Member registerMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    public Member findMember(String email){
        return memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new EntityNotFoundException("사용자가 존재하지 않습니다.");
        });
    }

    private void validateDuplicateMember(Member member){
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 가입된 회원입니다.");
                });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public MemberFormDto getMember(String memberEmail, String email) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("사용자가 존재하지 않습니다.");
                });

        if(!StringUtils.equals(member.getEmail(), email)){
            throw new IllegalStateException("회원정보가 일치하지 않습니다.");
        }

        return MemberFormDto.from(member);
    }

    public void updateMember(MemberFormDto memberFormDto) {
        Member member = memberRepository.findByEmail(memberFormDto.getEmail())
                .orElseThrow(EntityNotFoundException::new);
        if(!memberFormDto.getName().isBlank() &&
                !memberFormDto.getName().equals(member.getName())) member.setName(memberFormDto.getName());
        if(!memberFormDto.getPassword().isBlank() &&
                !passwordEncoder.matches(memberFormDto.getPassword(), member.getPassword())) member.setPassword(passwordEncoder.encode(memberFormDto.getPassword()));
    }

    public void deleteMember(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("사용자가 존재하지 않습니다.");
                });
        memberRepository.delete(member);
    }
}
