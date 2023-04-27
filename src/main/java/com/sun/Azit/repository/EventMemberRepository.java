package com.sun.Azit.repository;

import com.sun.Azit.entity.Event;
import com.sun.Azit.entity.EventMember;
import com.sun.Azit.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventMemberRepository extends JpaRepository<EventMember, Long> {

    Optional<EventMember> findByMember(Member member);
    Optional<EventMember> findByMemberAndEventId(Member member, Long EventId);
    Page<EventMember> findByEvent(Event event, Pageable pageable);

    void deleteByMemberIdAndEventId(Long memberId, Long eventId);

    Optional<EventMember> findByMemberIdAndEventId(Long memberId, Long eventId);
}
