package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.exceptionHandling.ResourceNotFoundException;
import com.example.baseballscoresheet.model.entities.LineupEntity;
import com.example.baseballscoresheet.repositories.LineupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineupService {

    private final LineupRepository lineupRepository;

    @Autowired
    public LineupService(com.example.baseballscoresheet.repositories.LineupRepository lineupRepository) {
        this.lineupRepository = lineupRepository;
    }

    public void saveLineup(LineupEntity lineupEntity) {
        this.lineupRepository.save(lineupEntity);
    }

    public LineupEntity findLineupByTeamId(Long teamId) {
        if (this.lineupRepository.findByTeam_Id(teamId) != null) {
            return lineupRepository.findByTeam_Id(teamId);
        } else {
            throw new ResourceNotFoundException("Lineup with team id: " + teamId + " not found");
        }
    }
}
