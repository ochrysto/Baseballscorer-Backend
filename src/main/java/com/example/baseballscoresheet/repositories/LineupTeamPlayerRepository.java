package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.LineupTeamPlayerEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineupTeamPlayerRepository extends JpaRepository<LineupTeamPlayerEntity, Long>, TruncateRepository {

    LineupTeamPlayerEntity findByTeamPlayerId(Long teamPlayerId);
    List<LineupTeamPlayerEntity> findLineupTeamPlayerEntitiesByLineup_Game_IdAndLineup_Team_IdOrderByPosition_Id(Long lineup_game_id, Long lineup_team_id);
}
