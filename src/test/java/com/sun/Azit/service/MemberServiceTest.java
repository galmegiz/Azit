package com.sun.Azit.service;

import com.sun.Azit.constant.Role;
import com.sun.Azit.entity.Member;
import com.sun.Azit.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("중복 가입 검증 테스트")
    void duplicateErrorTest() {
        Member member = Member.of("Sun", "somethingsjw@naver.com", "12345678", Role.USER);
        memberService.registerMember(member);
        Member member2 = Member.of("Sun1", "somethingsjw@naver.com", "12345678", Role.USER);
        Assertions.assertThatThrownBy(() -> memberService.registerMember(member2))
                .isInstanceOf(IllegalStateException.class);
    }
}