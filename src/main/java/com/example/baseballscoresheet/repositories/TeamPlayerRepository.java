package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.TeamPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamPlayerRepository extends JpaRepository<TeamPlayerEntity, Long> {

    boolean existsByPlayer_Id(Long playerId);

    TeamPlayerEntity findTeamPlayerEntityByPlayer_Id(Long playerId);
}
