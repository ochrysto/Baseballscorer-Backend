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
        return playerService.findPlayerById(playerId).isPresent();
    }

    // checks whether a team with the transferred id exists in database
    public static boolean checkIfTeamExists(Long teamId) {
        return teamService.findTeamById(teamId).isPresent();
    }


    // checks whether the transferred player is already assigned to a team
    public static boolean isPlayerAssignedToATeam(Long playerId) {
        return playerService.isPlayerAssignedToATeam(playerId);
    }

    // checks whether a manager with the transferred id exists in database and returns it if the check is successful
    public static ManagerEntity returnManagerIfExists(Long managerId) {
        ManagerEntity managerEntity;
        Optional<ManagerEntity> managerOptional = managerService.getManagerById(managerId);
        if (managerOptional.isPresent()) {
            managerEntity = managerOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager with id " + managerId + " was not found.");
        }
        return managerEntity;
    }

    // checks whether a club with the transferred id exists in database and returns it if the check is successful
    public static ClubEntity returnClubIfExists(Long clubId) {
        ClubEntity clubEntity;
        Optional<ClubEntity> clubOptional = clubService.getClubById(clubId);
        if (clubOptional.isPresent()) {
            clubEntity = clubOptional.get();
        } else {
            // throws exception if no suitable club was found in DB
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Club with id " + clubId + "was not found.");
        }
        return clubEntity;
    }

    // checks whether a league with the transferred id exists in database and returns it if the check is successful
    public static LeagueEntity returnLeagueIfExists(Long leagueId) {
        LeagueEntity leagueEntity;
        Optional<LeagueEntity> leagueOptional = leagueService.getLeagueById(leagueId);
        if (leagueOptional.isPresent()) {
            leagueEntity = leagueOptional.get();
        } else {
            // throws exception if no suitable league was found in DB
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "League with id " + leagueId + "was not found.");
        }
        return leagueEntity;
    }

    // checks if a team with the transferred id exists in database and returns it if the check is successful
    public static TeamEntity returnTeamIfExists(Long id) {
        TeamEntity teamEntity;
        Optional<TeamEntity> teamOptional = teamService.findTeamById(id);
        if (teamOptional.isPresent()) {
            teamEntity = teamOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team with id " + id + " was not found.");
        }
        return teamEntity;
    }

    // checks whether a player with the transferred id exists in database and returns it if the check is successful
    public static PlayerEntity returnPlayerIfExists(Long playerId) {
        PlayerEntity playerEntity;
        Optional<PlayerEntity> playerOptional = playerService.findPlayerById(playerId);
        if (playerOptional.isPresent()) {
            playerEntity = playerOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player with id " + playerId + " was not found.");
        }
        return playerEntity;
    }
}