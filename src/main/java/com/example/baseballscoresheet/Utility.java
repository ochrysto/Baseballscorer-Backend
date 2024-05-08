package com.example.baseballscoresheet;

import com.example.baseballscoresheet.exceptionHandling.RessourceNotFoundException;
import com.example.baseballscoresheet.model.LeagueEntity;
import com.example.baseballscoresheet.model.ManagerEntity;
import com.example.baseballscoresheet.model.PlayerEntity;
import com.example.baseballscoresheet.model.TeamEntity;
import com.example.baseballscoresheet.model.ClubEntity;
import com.example.baseballscoresheet.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    // prüft, ob der übergebene Spieler bereits einem Team zugeordnet ist
    // gibt boolean zurück
    public static boolean isPlayerAssignedToATeam(Long playerId) {
        return playerService.isPlayerAssignedToATeam(playerId);
    }

    public static ManagerEntity returnManagerIfExists(Long managerId) {
        ManagerEntity managerEntity;
        Optional<ManagerEntity> managerOptional = managerService.getManagerById(managerId);
        if (managerOptional.isPresent()) {
            // adds ManagerEntity to TeamEntity if a DB entry is found
            managerEntity = managerOptional.get();
        } else {
            // throws exception if no suitable manager was found in DB
            throw new RessourceNotFoundException("Manager with id " + managerId + " was not found.");
        }
        return managerEntity;
    }

    public static ClubEntity returnClubIfExists(Long clubId) {
        ClubEntity clubEntity;
        Optional<ClubEntity> clubOptional = clubService.getClubById(clubId);
        if (clubOptional.isPresent()) {
            // adds ClubEntity to TeamEntity if a DB entry is found
            clubEntity = clubOptional.get();
        } else {
            // throws exception if no suitable club was found in DB
            throw new RessourceNotFoundException("Club with id " + clubId + "was not found.");
        }
        return clubEntity;
    }

    public static LeagueEntity returnLeagueIfExists(Long leagueId) {
        LeagueEntity leagueEntity;
        Optional<LeagueEntity> leagueOptional = leagueService.getLeagueById(leagueId);
        if (leagueOptional.isPresent()) {
            // adds LeagueEntity to TeamEntity if a DB entry is found
            leagueEntity = leagueOptional.get();
        } else {
            // throws exception if no suitable league was found in DB
            throw new RessourceNotFoundException("League with id " + leagueId + "was not found.");
        }
        return leagueEntity;
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