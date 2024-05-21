package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.TeamPlayerEntity;
import com.example.baseballscoresheet.repositories.TeamPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TeamPlayerService {
    private final TeamPlayerRepository teamPlayerRepository;

    @Autowired
    public TeamPlayerService(TeamPlayerRepository teamPlayerRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
    }

    public void saveTeamPlayer(TeamPlayerEntity teamPlayerEntity) {
        this.teamPlayerRepository.save(teamPlayerEntity);
    }

    public TeamPlayerEntity findTeamPlayerEntityByTeamIdAndPlayerId(Long teamId, Long playerId) {
        if (this.teamPlayerRepository.findTeamPlayerEntityByTeam_IdAndPlayer_Id(teamId, playerId) != null) {
            return this.teamPlayerRepository.findTeamPlayerEntityByTeam_IdAndPlayer_Id(teamId, playerId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TeamPlayerEntity not found");
        }
    }

    public TeamPlayerEntity findTeamPlayerEntityByPlayerId(Long playerId) {
        if (this.teamPlayerRepository.findByPlayer_Id(playerId) != null) {
            return teamPlayerRepository.findByPlayer_Id(playerId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TeamPlayerEntity not found");
        }
    }
}