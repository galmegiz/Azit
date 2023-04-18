package com.sun.Azit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sun.Azit.entity.Member;

import java.util.Optional;

public interface SpringJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    Optional<Member> findByName(String name);
    @Override
    Optional<Member> findByEmail(String email);
}
