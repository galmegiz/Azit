package com.sun.Azit.repository;

import com.sun.Azit.entity.Club;
import com.sun.Azit.entity.ClubImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubImgRepository extends JpaRepository<ClubImg, Long> {
    Optional<List<ClubImg>> findByClub(Club club);
}
