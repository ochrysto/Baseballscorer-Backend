package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.LineupEntity;
import com.example.baseballscoresheet.repositories.LineupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public LineupEntity findLineupById(Long lineupId) {
        if (this.lineupRepository.findById(lineupId).isPresent()) {
            return this.lineupRepository.findById(lineupId).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lineup with id " + lineupId + " not found");
        }
    }

    public LineupEntity findLineupByTeamId(Long teamId) {
        if (this.lineupRepository.findByTeam_Id(teamId) != null) {
            return lineupRepository.findByTeam_Id(teamId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lineup with team (teamID:) " + teamId + " not found");
        }
    }
}
