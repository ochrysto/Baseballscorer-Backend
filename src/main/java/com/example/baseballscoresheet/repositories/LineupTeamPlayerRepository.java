package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.LineupTeamPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineupTeamPlayerRepository extends JpaRepository<LineupTeamPlayerEntity, Long> {

    LineupTeamPlayerEntity findByTeamPlayerId(Long teamPlayerId);
}
