package com.example.baseballscoresheet;

import com.example.baseballscoresheet.model.LeagueEntity;
import com.example.baseballscoresheet.model.ManagerEntity;
import com.example.baseballscoresheet.model.PlayerEntity;
import com.example.baseballscoresheet.model.TeamEntity;
import com.example.baseballscoresheet.model.ClubEntity;
import com.example.baseballscoresheet.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class Utility {

    private static PlayerService playerService;
    private static TeamService teamService;
    private static ManagerService managerService;
    private static ClubService clubService;
    private static LeagueService leagueService;


    @Autowired
    public void setPlayerService(PlayerService playerService, TeamService teamService, ManagerService managerService,
                                 ClubService clubService, LeagueService leagueService) {
        Utility.playerService = playerService;
        Utility.teamService = teamService;
        Utility.managerService = managerService;
        Utility.clubService = clubService;
        Utility.leagueService = leagueService;
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