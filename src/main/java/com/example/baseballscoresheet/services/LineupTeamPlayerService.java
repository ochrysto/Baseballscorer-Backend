package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.LineupTeamPlayerEntity;
import com.example.baseballscoresheet.repositories.LineupTeamPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LineupTeamPlayerService {

    private final LineupTeamPlayerRepository repository;

    @Autowired
    public LineupTeamPlayerService(LineupTeamPlayerRepository lineupTeamPlayerRepository) {
        this.repository = lineupTeamPlayerRepository;
    }

    public LineupTeamPlayerEntity saveLineupTeamPlayerEntity(LineupTeamPlayerEntity lineupTeamPlayerEntity) {
        return this.repository.save(lineupTeamPlayerEntity);
    }

    public LineupTeamPlayerEntity findByTeamPlayerId(Long teamPlayerId) {
        return this.repository.findByTeamPlayerId(teamPlayerId);
    }

    public List<LineupTeamPlayerEntity> findByGameAndTeam(long game_id, long team_id) {
        return this.repository.findLineupTeamPlayerEntitiesByLineup_Game_IdAndLineup_Team_IdOrderByPosition_Id(game_id, team_id);
    }

    public Optional<LineupTeamPlayerEntity> getNextLineupTeamPlayerByLineupTeamPlayer(LineupTeamPlayerEntity lineupTeamPlayer) {
        // We should search using position number AND concrete lineup
        // Otherwise we will get `Query did not return a unique result: 2 results were returned`
        int position = lineupTeamPlayer.getPosition().getPosition() + 1;
        return repository.findLineupTeamPlayerEntityByPositionPositionAndLineup(position, lineupTeamPlayer.getLineup());
    }
}