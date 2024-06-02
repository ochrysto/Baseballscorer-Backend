package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.LineupTeamPlayerEntity;
import com.example.baseballscoresheet.repositories.LineupTeamPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineupTeamPlayerService {

    private final LineupTeamPlayerRepository lineupTeamPlayerRepository;

    @Autowired
    public LineupTeamPlayerService(LineupTeamPlayerRepository lineupTeamPlayerRepository) {
        this.lineupTeamPlayerRepository = lineupTeamPlayerRepository;
    }

    public void saveLineupTeamPlayerEntity(LineupTeamPlayerEntity lineupTeamPlayerEntity) {
        this.lineupTeamPlayerRepository.save(lineupTeamPlayerEntity);
    }

    public LineupTeamPlayerEntity findByTeamPlayerId(Long teamPlayerId) {
        return this.lineupTeamPlayerRepository.findByTeamPlayerId(teamPlayerId);
    }
}