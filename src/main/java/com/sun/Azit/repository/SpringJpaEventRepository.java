//package com.sun.Azit.repository;
//
//import com.sun.Azit.domain.Event;
//import com.sun.Azit.domain.Member;
//import com.sun.Azit.dto.EventDto;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface SpringJpaEventRepository extends JpaRepository<Event, Long>, EventRepository {
//    @Override
//    Page<Event> findByTitle(String title, Pageable pageable);
//}
