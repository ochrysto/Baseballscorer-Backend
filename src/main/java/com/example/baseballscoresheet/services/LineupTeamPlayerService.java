package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.LineupTeamPlayerEntity;
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

    public List<LineupTeamPlayerEntity> findAll() {
        return this.lineupTeamPlayerRepository.findAll();
    }
}