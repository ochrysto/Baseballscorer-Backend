package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.Utility;
import com.example.baseballscoresheet.exceptionHandling.PlayerIsPartOfATeamException;
import com.example.baseballscoresheet.exceptionHandling.RessourceNotFoundException;
import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.*;
import com.example.baseballscoresheet.model.dto.team.AddTeamInfoDto;
import com.example.baseballscoresheet.model.dto.team.GetTeamDto;
import com.example.baseballscoresheet.model.dto.team.GetTeamInfoDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/team")
public class TeamController {

    private final MappingService mappingService;
    private final TeamService teamService;
    private final ManagerService managerService;
    private final ClubService clubService;
    private final LeagueService leagueService;
    private final TeamPlayerService teamPlayerService;

    public TeamController(MappingService mappingService, TeamService teamService, ManagerService managerService,
                          ClubService clubService, LeagueService leagueService, TeamPlayerService teamPlayerService) {
        this.mappingService = mappingService;
        this.teamService = teamService;
        this.managerService = managerService;
        this.clubService = clubService;
        this.leagueService = leagueService;
        this.teamPlayerService = teamPlayerService;
    }

    // Endpoint for saving a new team
    @Operation(summary = "saves a new team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created team",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTeamInfoDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PostMapping
    //@RolesAllowed("user")
    public ResponseEntity<GetTeamInfoDto> createTeam(@RequestBody @Valid AddTeamInfoDto addTeamInfoDto) {
        ManagerEntity managerEntity;
        ClubEntity clubEntity;
        LeagueEntity leagueEntity;
        // searches for manager in DB using the managerId
        Optional<ManagerEntity> managerOptional = managerService.getManagerById(addTeamInfoDto.getManagerId());
        if (managerOptional.isPresent()) {
            // adds ManagerEntity object to TeamEntity object if a DB entry is found
            managerEntity = managerOptional.get();
        } else {
            // throws exception if no suitable manager was found in DB
            throw new RessourceNotFoundException("Manager with id " + addTeamInfoDto.getManagerId() + " was not found.");
        }
        // searches for club in DB using the clubId
        Optional<ClubEntity> clubOptional = clubService.getClubById(addTeamInfoDto.getClubId());
        if (clubOptional.isPresent()) {
            // adds ClubEntity object to TeamEntity object if a DB entry is found
            clubEntity = clubOptional.get();
        } else {
            // throws exception if no suitable club was found in DB
            throw new RessourceNotFoundException("Club with id " + addTeamInfoDto.getClubId() + "was not found.");
        }
        // searches for league in DB using the leagueId
        Optional<LeagueEntity> leagueOptional = leagueService.getLeagueById(addTeamInfoDto.getLeagueId());
        if (leagueOptional.isPresent()) {
            // adds LeagueEntity object to TeamEntity object if a DB entry is found
            leagueEntity = leagueOptional.get();
        } else {
            // throws exception if no suitable league was found in DB
            throw new RessourceNotFoundException("League with id " + addTeamInfoDto.getLeagueId() + "was not found.");
        }
        // maps TeamDto object to TeamEntity object and saves it in the database
        TeamEntity teamEntity = this.mappingService.mapAddTeamInfoDtoToTeamEntity(
                addTeamInfoDto, managerEntity, clubEntity, leagueEntity);
        teamEntity = this.teamService.createTeam(teamEntity);
        GetTeamInfoDto addedTeam = this.mappingService.mapTeamToGetTeamInfoDto(teamEntity);
        return new ResponseEntity<>(addedTeam, HttpStatus.CREATED);
    }

    // Endpoint to retrieve all existing teams
    @Operation(summary = "retrieve all existing teams")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "teams found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTeamInfoDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "teams not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetTeamInfoDto>> findAllTeams() {
        // searches the database for all teams and adds them to a list
        List<TeamEntity> teamEntities = this.teamService.findAll();
        List<GetTeamInfoDto> teamDtos = new LinkedList<>();
        // maps each TeamEntity object found from the list to a GetTeamInfoDto object
        // adds each GetTeamInfoDto object to a list
        for (TeamEntity teamEntity : teamEntities) {
            teamDtos.add(this.mappingService.mapTeamToGetTeamInfoDto(teamEntity));
        }
        // returns a list with GetTeamInfoDto objects
        return new ResponseEntity<>(teamDtos, HttpStatus.OK);
    }

    // Endpoint to retrieve information about a specific team by id
    @Operation(summary = "retrieve all information of a specific team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "team found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTeamInfoDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "team not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetTeamInfoDto> findTeamById(@PathVariable Long id) {
        TeamEntity teamEntity;
        // search for team by id in the database
        teamEntity = Utility.returnTeamIfExists(id);
        // maps the found team entity object to a GetTeamInfoDto object
        GetTeamInfoDto getTeamInfoDto = this.mappingService.mapTeamToGetTeamInfoDto(teamEntity);
        // returns the mapped GetTeamInfoDto object
        return new ResponseEntity<>(getTeamInfoDto, HttpStatus.OK);
    }

    // Endpoint for updating an existing team by id
    @Operation(summary = "updates a existing team",
            description = "team must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "team found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTeamInfoDto.class))}),
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
    public ResponseEntity<GetTeamInfoDto> updateTeamInfo(@PathVariable final Long id,
                                                         @Valid @RequestBody final AddTeamInfoDto updateTeamDto) {
        ManagerEntity managerEntity;
        ClubEntity clubEntity;
        LeagueEntity leagueEntity;
        // searches for manager in DB using the managerId
        managerEntity = Utility.returnManagerIfExists(updateTeamDto.getManagerId());
        // searches for club in DB using the clubId
        clubEntity = Utility.returnClubIfExists(updateTeamDto.getClubId());
        // searches for league in DB using the leagueId
        leagueEntity = Utility.returnLeagueIfExists(updateTeamDto.getLeagueId());

        // maps all information and the id to a TeamEntity object
        TeamEntity updatedTeamEntity = this.mappingService.mapAddTeamInfoDtoToTeamEntity(
                updateTeamDto, managerEntity, clubEntity, leagueEntity);
        updatedTeamEntity.setId(id);
        // the mapped TeamEntity object is passed on to the TeamService
        // the service checks whether the TeamEntity object that is to be updated in the database actually exists in the database
        // if not, a ResourceNotFoundException is thrown
        // if the corresponding TeamEntity object is found, its attribute values are updated with the attribute values of the transferred TeamEntity object
        // the updated TeamEntity object is stored in the database
        updatedTeamEntity = this.teamService.update(updatedTeamEntity);
        // the mapped TeamEntity object is mapped to a GetTeamInfoDto object and returned
        GetTeamInfoDto updatedTeamDto = this.mappingService.mapTeamToGetTeamInfoDto(updatedTeamEntity);
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
        // check whether the id is present in the database
        if (teamService.findTeamById(id).isPresent()) {
            // if yes, then the data record with the id is deleted
            this.teamService.delete(id);
        } else {
            // if not, then a ResourceNotFoundException is thrown
            throw new RessourceNotFoundException("Team with id: " + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint to add one or more players to a existing team
    @Operation(summary = "saves a new team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "added player(s) to team",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTeamInfoDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("{teamId}")
    public ResponseEntity<GetTeamDto> addPlayersToTeam(@PathVariable Long teamId,
                                                       @RequestBody List<Long> playerList) {
        // searches for team data record with the passed id
        // returns team when found
        TeamEntity teamEntity = Utility.returnTeamIfExists(teamId);
        // creates new set with PlayerEntity objects
        Set<PlayerEntity> players = new HashSet<>();
        // iterates over the transferred list with player ids
        for (Long playerId : playerList) {
            // searches for PlayerEntity object in database using player id from list
            // returns PlayerEntity object when found in database
            PlayerEntity playerEntity = Utility.returnPlayerIfExists(playerId);
            //checks whether the PlayerEntity object found is already assigned to a team
            // if not, then adding it to the list of PlayerEntity objects
            if (!Utility.isPlayerAssignedToATeam(playerEntity.getId())) {
                players.add(playerEntity);
            } else {
                //if yes, then a PlayerIsPartOfATeamException is thrown
                throw new PlayerIsPartOfATeamException("Player with the id: " + " is already assigned to another team.");
            }
        }
        // it is iterated over the list currently filled with PlayerEntity objects
        // each PlayerEntity is assigned to a new TeamPlayerEntity object
        // the TeamEntity object is the team with the transferred teamId
        for (PlayerEntity playerEntity : players) {
            TeamPlayerEntity teamPlayerEntity = new TeamPlayerEntity();
            teamPlayerEntity.setPlayer(playerEntity);
            teamPlayerEntity.setTeam(teamEntity);
            // the TeamPlayerEntity object is saved in database
            this.teamPlayerService.createTeamPlayer(teamPlayerEntity);
        }
        // mapping and returning the TeamEntity object
        GetTeamDto getTeamDto = this.mappingService.mapTeamEntityToGetTeamDto(teamEntity);
        return new ResponseEntity<>(getTeamDto, HttpStatus.CREATED);
    }
}