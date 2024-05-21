package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.TeamPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamPlayerRepository extends JpaRepository<TeamPlayerEntity, Long> {

    boolean existsByPlayer_Id(Long playerId);

    void deleteByPlayer_IdAndTeam_Id(Long player_id, Long team_id);

    TeamPlayerEntity findTeamPlayerEntityByTeam_IdAndPlayer_Id(Long team_id, Long player_id);

    TeamPlayerEntity findByPlayer_Id(Long playerId);
}
