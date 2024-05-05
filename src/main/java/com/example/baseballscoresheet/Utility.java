package com.example.baseballscoresheet;

import com.example.baseballscoresheet.exceptionHandling.RessourceNotFoundException;
import com.example.baseballscoresheet.model.PlayerEntity;
import com.example.baseballscoresheet.model.TeamEntity;
import com.example.baseballscoresheet.services.PlayerService;
import com.example.baseballscoresheet.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Utility {

    private static PlayerService playerService;
    private static TeamService teamService;

    @Autowired
    public void setPlayerService(PlayerService playerService, TeamService teamService) {
        Utility.playerService = playerService;
        Utility.teamService = teamService;
    }

    public static TeamEntity returnTeamIfExists(Long id) {
        TeamEntity teamEntity;
        Optional<TeamEntity> teamOptional = teamService.findTeamById(id);
        if (teamOptional.isPresent()) {
            teamEntity = teamOptional.get();
        } else {
            // throws exception if no suitable team was found
            throw new RessourceNotFoundException("Team with id " + id + " was not found.");
        }
        return teamEntity;
    }

    public static PlayerEntity returnPlayerIfExists(Long playerId) {
        PlayerEntity playerEntity;
        Optional<PlayerEntity> playerOptional = playerService.findPlayerById(playerId);
        if (playerOptional.isPresent()) {
            playerEntity = playerOptional.get();
        } else {
            // throws exception if no suitable player was found in DB
            throw new RessourceNotFoundException("Player with id " + playerId + " was not found.");
        }
        return playerEntity;
    }
}