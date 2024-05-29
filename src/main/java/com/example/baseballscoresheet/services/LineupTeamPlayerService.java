package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.LineupTeamPlayerEntity;
import com.example.baseballscoresheet.repositories.LineupTeamPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineupTeamPlayerService {

    private final LineupTeamPlayerRepository lineupTeamPlayerRepository;

    @Autowired
    public LineupTeamPlayerService(LineupTeamPlayerRepository lineupTeamPlayerRepository) {
        this.lineupTeamPlayerRepository = lineupTeamPlayerRepository;
    }

    public LineupTeamPlayerEntity saveLineupTeamPlayerEntity(LineupTeamPlayerEntity lineupTeamPlayerEntity) {
        return this.lineupTeamPlayerRepository.save(lineupTeamPlayerEntity);
    }

    public LineupTeamPlayerEntity findByTeamPlayerId(Long teamPlayerId) {
        return this.lineupTeamPlayerRepository.findByTeamPlayerId(teamPlayerId);
    }

    public List<LineupTeamPlayerEntity> findByGameAndTeam(long game_id, long team_id) {
        return this.lineupTeamPlayerRepository.findLineupTeamPlayerEntitiesByLineup_Game_IdAndLineup_Team_IdOrderByPosition_Id(game_id, team_id);
    }
}