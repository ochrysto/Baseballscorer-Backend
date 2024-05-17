package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.LineupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineupRepository extends JpaRepository<LineupEntity, Long> {
    LineupEntity findByTeam_Id(Long id);
}


