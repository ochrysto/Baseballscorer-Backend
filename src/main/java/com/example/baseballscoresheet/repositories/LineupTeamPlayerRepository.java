package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.LineupTeamPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineupTeamPlayerRepository extends JpaRepository<LineupTeamPlayerEntity, Long> {

    LineupTeamPlayerEntity findByTeamPlayerId(Long teamPlayerId);
}
