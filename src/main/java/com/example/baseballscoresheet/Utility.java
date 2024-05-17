package com.example.baseballscoresheet;

import com.example.baseballscoresheet.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utility {

    private static PlayerService playerService;
    private static TeamService teamService;


    @Autowired
    public void setPlayerService(PlayerService playerService, TeamService teamService) {
        Utility.playerService = playerService;
        Utility.teamService = teamService;
    }

    // checks whether a player with the transferred id exists in database
    public static boolean checkIfPlayerExists(Long playerId) {
        return playerService.findPlayerById(playerId) == null;
    }

    // checks whether a team with the transferred id exists in database
    public static boolean checkIfTeamExists(Long teamId) {
        return teamService.findTeamById(teamId) == null;
    }


    // checks whether the transferred player is already assigned to a team
    public static boolean isPlayerAssignedToATeam(Long playerId) {
        return playerService.isPlayerAssignedToATeam(playerId);
    }
}