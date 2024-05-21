package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {
}
