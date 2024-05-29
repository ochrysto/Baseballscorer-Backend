package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.LineupTeamPlayerEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineupTeamPlayerRepository extends JpaRepository<LineupTeamPlayerEntity, Long>, TruncateRepository {

    LineupTeamPlayerEntity findByTeamPlayerId(Long teamPlayerId);
}
