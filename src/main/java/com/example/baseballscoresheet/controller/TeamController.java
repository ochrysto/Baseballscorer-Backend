package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.Utility;
import com.example.baseballscoresheet.exceptionHandling.PlayerIsNotAvailableException;
import com.example.baseballscoresheet.exceptionHandling.ResourceNotFoundException;
import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.dtos.player.GetPlayerDto;
import com.example.baseballscoresheet.model.dtos.team.AddTeamWithoutPlayerListDto;
import com.example.baseballscoresheet.model.dtos.team.GetTeamWithPlayerListDto;
import com.example.baseballscoresheet.model.dtos.team.GetTeamWithoutPlayerListDto;
import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/team")
public class TeamController {

    private final MappingService mappingService;
    private final TeamService teamService;
    private final TeamPlayerService teamPlayerService;
    private final PlayerService playerService;
    private final ManagerService managerService;
    private final LeagueService leagueService;
    private final ClubService clubService;

    public TeamController(MappingService mappingService, TeamService teamService, TeamPlayerService teamPlayerService,
                          PlayerService playerService, ManagerService managerService, LeagueService leagueService,
                          ClubService clubService) {
        this.mappingService = mappingService;
        this.teamService = teamService;
        this.teamPlayerService = teamPlayerService;
        this.playerService = playerService;
        this.managerService = managerService;
        this.leagueService = leagueService;
        this.clubService = clubService;
    }

    // Endpoint for saving a new team
    @Operation(summary = "saves a new team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created team",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTeamWithoutPlayerListDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PostMapping
    @RolesAllowed("user")
    public ResponseEntity<GetTeamWithoutPlayerListDto> createTeam(@RequestBody @Valid AddTeamWithoutPlayerListDto addTeamWithoutPlayerListDto) {
        // searches for club, manager and league and returns them if they are found in the database
        ManagerEntity managerEntity = this.managerService.getManagerById(addTeamWithoutPlayerListDto.getManagerId());
        ClubEntity clubEntity = this.clubService.getClubById(addTeamWithoutPlayerListDto.getClubId());
        LeagueEntity leagueEntity = this.leagueService.getLeagueById(addTeamWithoutPlayerListDto.getLeagueId());

        // maps TeamDto object to TeamEntity object and saves it in the database
        TeamEntity teamEntity = this.mappingService.mapToTeamEntity(
                addTeamWithoutPlayerListDto, managerEntity, clubEntity, leagueEntity);
        teamEntity = this.teamService.createTeam(teamEntity);
        GetTeamWithoutPlayerListDto addedTeam = this.mappingService.mapTeamEntityToGetTeamWithoutPlayerListDto(teamEntity);
        return new ResponseEntity<>(addedTeam, HttpStatus.CREATED);
    }

    // Endpoint to retrieve all existing teams
    @Operation(summary = "retrieve all existing teams")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "teams found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTeamWithoutPlayerListDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "teams not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetTeamWithoutPlayerListDto>> findAllTeams() {
        // searches the database for all teams and adds them to a list
        List<TeamEntity> teamEntities = this.teamService.findAll();
        List<GetTeamWithoutPlayerListDto> teamDtos = new LinkedList<>();
        // maps each TeamEntity object found from the list to a GetTeamInfoDto object
        // adds each GetTeamInfoDto object to a list
        for (TeamEntity teamEntity : teamEntities) {
            teamDtos.add(this.mappingService.mapTeamToGetTeamWithoutPlayerListDto(teamEntity));
        }
        // returns a list with GetTeamInfoDto objects
        return new ResponseEntity<>(teamDtos, HttpStatus.OK);
    }

    // Endpoint to retrieve information about a specific team by id
    @Operation(summary = "retrieve all information of a specific team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "team found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTeamWithoutPlayerListDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "team not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @GetMapping("/{teamId}")
    @RolesAllowed("user")
    public ResponseEntity<GetTeamWithoutPlayerListDto> findTeamById(@PathVariable Long teamId) {
        TeamEntity teamEntity = this.teamService.findTeamById(teamId);
        GetTeamWithoutPlayerListDto getTeamWithoutPlayerListDto
                = this.mappingService.mapTeamEntityToGetTeamWithoutPlayerListDto(teamEntity);
        return new ResponseEntity<>(getTeamWithoutPlayerListDto, HttpStatus.OK);
    }

    // Endpoint for updating an existing team by id
    @Operation(summary = "updates a existing team",
            description = "team must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "team found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTeamWithoutPlayerListDto.class))}),
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "team not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetTeamWithoutPlayerListDto> updateTeamInfo(@PathVariable final Long id,
                                                                      @Valid @RequestBody final AddTeamWithoutPlayerListDto updateTeamDto) {
        // searches for club, manager and league and returns them if they are found in the database
        ManagerEntity managerEntity = this.managerService.getManagerById(updateTeamDto.getManagerId());
        ClubEntity clubEntity = this.clubService.getClubById(updateTeamDto.getClubId());
        LeagueEntity leagueEntity = this.leagueService.getLeagueById(updateTeamDto.getLeagueId());

        // maps all information and the id to a TeamEntity object
        TeamEntity updatedTeamEntity = this.mappingService.mapToTeamEntity(updateTeamDto, managerEntity, clubEntity, leagueEntity);
        updatedTeamEntity.setId(id);
        // service checks whether the TeamEntity object that is to be updated in the database actually exists in the database
        // if its found, its attribute values are updated with the attribute values of the transferred TeamEntity object
        // updated TeamEntity object is stored in the database
        updatedTeamEntity = this.teamService.update(updatedTeamEntity);
        // the mapped TeamEntity object is mapped to a GetTeamInfoDto object and returned
        GetTeamWithoutPlayerListDto updatedTeamDto = this.mappingService.mapTeamToGetTeamWithoutPlayerListDto(updatedTeamEntity);
        return new ResponseEntity<>(updatedTeamDto, HttpStatus.CREATED);
    }

    // Endpoint to delete an existing team by id
    @Operation(summary = "deletes team by id",
            description = "team must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "team not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @RolesAllowed("user")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteTeamById(@PathVariable Long id) {
        this.teamService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint to add players to an existing team
    @Operation(summary = "add players to an existing team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "added player(s) to team",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTeamWithoutPlayerListDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("{teamId}/players")
    public ResponseEntity<GetTeamWithPlayerListDto> addPlayersToTeam(@PathVariable Long teamId,
                                                                     @RequestBody @Valid List<Long> playerList) {
        // searches for team data record with the passed id and return when found
        TeamEntity teamEntity = teamService.findTeamById(teamId);
        Set<PlayerEntity> players = new HashSet<>();
        // iterates over the list with ids, searches for the matching player objects in the database and returns when found
        for (Long playerId : playerList) {
            PlayerEntity playerEntity = this.playerService.findPlayerById(playerId);
            //checks whether the PlayerEntity object found is already assigned to a team
            players.add(playerEntity);
        }
        // iterate over the list with PlayerEntity objects, each PlayerEntity is assigned to a new TeamPlayerEntity object
        // saves new TeamPlayerEntity
        for (PlayerEntity playerEntity : players) {
            TeamPlayerEntity teamPlayerEntity = new TeamPlayerEntity();
            teamPlayerEntity.setPlayer(playerEntity);
            teamPlayerEntity.setTeam(teamEntity);
            this.teamPlayerService.saveTeamPlayer(teamPlayerEntity);
        }
        // mapping and returning the TeamEntity object
        GetTeamWithPlayerListDto getTeamWithPlayerListDto = this.mappingService.mapTeamEntityToGetTeamWithPlayerListDto(teamEntity);
        return new ResponseEntity<>(getTeamWithPlayerListDto, HttpStatus.CREATED);
    }

    // Endpoint to remove a player from a team
    @Operation(summary = "remove player from an existing team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "removed player from team",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTeamWithoutPlayerListDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @DeleteMapping("{teamId}/{playerId}")
    @Transactional
    public ResponseEntity<GetTeamWithPlayerListDto> removePlayerFromTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        // checks whether team and player exist
        if (Utility.checkIfPlayerExists(playerId) && Utility.checkIfTeamExists(teamId)) {
            teamService.deletePlayerFromTeam(teamId, playerId);
        } else {
            throw new ResourceNotFoundException("Team with id: " + teamId + " or player with id: " + playerId + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint to retrieve all existing teams
    @Operation(summary = "retrieve all players from one existing teams")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "teams found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlayerDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "team not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping("/{teamId}/players")
    @RolesAllowed("user")
    public ResponseEntity<List<GetPlayerDto>> getAllPlayersFromTeam(@PathVariable Long teamId) {
        List<GetPlayerDto> allPlayersDto = new ArrayList<>();
        List<PlayerEntity> allPlayers;
        if (Utility.checkIfTeamExists(teamId)) {
            allPlayers = teamService.getAllPlayersFromTeam(teamId);
            for (PlayerEntity player : allPlayers) {
                allPlayersDto.add(this.mappingService.mapPlayerEntityToGetPlayerDto(player));
            }
        } else {
            throw new ResourceNotFoundException("Team with id: " + teamId + " not found");
        }
        return new ResponseEntity<>(allPlayersDto, HttpStatus.OK);
    }
}