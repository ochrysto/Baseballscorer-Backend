package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.LineupEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineupRepository extends JpaRepository<LineupEntity, Long>, TruncateRepository {
    LineupEntity findByTeam_Id(Long id);
}


