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

        // searches for managers in DB using the managerId
        Optional<ManagerEntity> managerOptional = managerService.getManagerById(addTeamInfoDto.getManagerId());
        if (managerOptional.isPresent()) {
            // adds ManagerEntity to TeamEntity if a DB entry is found
            managerEntity = managerOptional.get();
        } else {
            // throws exception if no suitable manager was found in DB
            throw new RessourceNotFoundException("Manager with id " + addTeamInfoDto.getManagerId() + " was not found.");
        }
        // searches for clubs in DB using the clubId
        Optional<ClubEntity> clubOptional = clubService.getClubById(addTeamInfoDto.getClubId());
        if (clubOptional.isPresent()) {
            // adds ClubEntity to TeamEntity if a DB entry is found
            clubEntity = clubOptional.get();
        } else {
            // throws exception if no suitable club was found in DB
            throw new RessourceNotFoundException("Club with id " + addTeamInfoDto.getClubId() + "was not found.");
        }
        // searches for leagues in DB using the leagueId
        Optional<LeagueEntity> leagueOptional = leagueService.getLeagueById(addTeamInfoDto.getLeagueId());
        if (leagueOptional.isPresent()) {
            // adds LeagueEntity to TeamEntity if a DB entry is found
            leagueEntity = leagueOptional.get();
        } else {
            // throws exception if no suitable league was found in DB
            throw new RessourceNotFoundException("League with id " + addTeamInfoDto.getLeagueId() + "was not found.");
        }
        // maps TeamDto to TeamEntity and saves it in the database
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
        List<TeamEntity> teamEntities = this.teamService.findAll();
        List<GetTeamInfoDto> teamDtos = new LinkedList<>();
        for (TeamEntity teamEntity : teamEntities) {
            teamDtos.add(this.mappingService.mapTeamToGetTeamInfoDto(teamEntity));
        }
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
        // searches and returns team using teamId
        teamEntity = Utility.returnTeamIfExists(id);
        GetTeamInfoDto getTeamInfoDto = this.mappingService.mapTeamToGetTeamInfoDto(teamEntity);
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
        // searches for managers in DB using the managerId
        managerEntity = Utility.returnManagerIfExists(updateTeamDto.getManagerId());
        // searches for clubs in DB using the clubId
        clubEntity = Utility.returnClubIfExists(updateTeamDto.getClubId());
        // searches for leagues in DB using the leagueId
        leagueEntity = Utility.returnLeagueIfExists(updateTeamDto.getLeagueId());
        // maps TeamDto to TeamEntity and saves it in the database
        TeamEntity updatedTeamEntity = this.mappingService.mapAddTeamInfoDtoToTeamEntity(
                updateTeamDto, managerEntity, clubEntity, leagueEntity);
        updatedTeamEntity.setId(id);
        updatedTeamEntity = this.teamService.update(updatedTeamEntity);
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
        if (id != null) {
            if (teamService.findTeamById(id).isPresent()) {
                this.teamService.delete(id);
            } else {
                throw new RessourceNotFoundException("Team with id: " + id + " not found");
            }
        } else {
            throw new RessourceNotFoundException("Id not valid.");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Endpoint for adding players to a team
     *
     * @param teamId     - id des Teams, zu dem Spieler hinzugefügt werden sollen
     * @param playerList - Liste der Spieler Ids, die zum Team hinzugefügt werden sollen
     * @return Objekt, das Informationen zum Team und die Spielerliste enthält
     */
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
    @PostMapping("{teamId}")
    public ResponseEntity<GetTeamDto> addPlayersToTeam(@PathVariable Long teamId,
                                                       @RequestBody List<Long> playerList) {

        // sucht nach Team mit der übergebenen teamId
        // gibt Team zurück, wenn es gefunden wird
        TeamEntity teamEntity = Utility.returnTeamIfExists(teamId);
        // erstellt neue Liste mit Player-Objekten
        // iteriert über die übergebene Liste mit player ids
        // sucht nach der id in DB
        // gibt player zurück, wenn er gefunden wird
        // und fügt ihn zur Liste mit Player-Objekten hinzu
        Set<PlayerEntity> players = new HashSet<>();
        for (Long playerId : playerList) {
            PlayerEntity playerEntity = Utility.returnPlayerIfExists(playerId);
            if (!Utility.isPlayerAssignedToATeam(playerEntity.getId())) {
                players.add(playerEntity);
            } else {
                throw new PlayerIsPartOfATeamException("Player with the id: " + " is already assigned to another team.");
            }
        }
        for (PlayerEntity playerEntity : players) {
            TeamPlayerEntity teamPlayerEntity = new TeamPlayerEntity();
            teamPlayerEntity.setPlayer(playerEntity);
            teamPlayerEntity.setTeam(teamEntity);
            this.teamPlayerService.createTeamPlayer(teamPlayerEntity);
        }
        GetTeamDto getTeamDto = this.mappingService.mapTeamEntityToGetTeamDto(teamEntity);
        return new ResponseEntity<>(getTeamDto, HttpStatus.CREATED);
    }
}