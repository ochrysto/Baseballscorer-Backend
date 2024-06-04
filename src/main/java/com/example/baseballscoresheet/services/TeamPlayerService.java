package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.exceptionHandling.ResourceNotFoundException;
import com.example.baseballscoresheet.model.entities.TeamPlayerEntity;
import com.example.baseballscoresheet.repositories.TeamPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerService {
    private final TeamPlayerRepository teamPlayerRepository;

    @Autowired
    public TeamPlayerService(TeamPlayerRepository teamPlayerRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
    }

    public TeamPlayerEntity saveTeamPlayer(TeamPlayerEntity teamPlayerEntity) {
        return this.teamPlayerRepository.save(teamPlayerEntity);
    }

    public TeamPlayerEntity findTeamPlayerEntityByTeamIdAndPlayerId(Long teamId, Long playerId) {
        if (this.teamPlayerRepository.findTeamPlayerEntityByTeam_IdAndPlayer_Id(teamId, playerId) != null) {
            return this.teamPlayerRepository.findTeamPlayerEntityByTeam_IdAndPlayer_Id(teamId, playerId);
        } else {
            throw new ResourceNotFoundException("TeamPlayerEntity with team id = " + teamId + " and player id = " + playerId + " not found");
        }
    }

    public TeamPlayerEntity findTeamPlayerEntityByPlayerId(Long playerId) {
        if (this.teamPlayerRepository.findByPlayer_Id(playerId) != null) {
            return teamPlayerRepository.findByPlayer_Id(playerId);
        } else {
            throw new ResourceNotFoundException("TeamPlayerEntity with player id = " + playerId + " not found");
        }
    }
}