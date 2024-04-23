package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.exceptionHandling.RessourceNotFoundException;
import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.ClubEntity;
import com.example.baseballscoresheet.model.LeagueEntity;
import com.example.baseballscoresheet.model.ManagerEntity;
import com.example.baseballscoresheet.model.TeamEntity;
import com.example.baseballscoresheet.model.dto.team.AddTeamInfoDto;
import com.example.baseballscoresheet.model.dto.team.GetTeamInfoDto;
import com.example.baseballscoresheet.services.ClubService;
import com.example.baseballscoresheet.services.LeagueService;
import com.example.baseballscoresheet.services.ManagerService;
import com.example.baseballscoresheet.services.TeamService;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/team")
public class TeamController {

    private final MappingService mappingService;
    private final TeamService teamService;
    private final ManagerService managerService;
    private final ClubService clubService;
    private final LeagueService leagueService;

    public TeamController(MappingService mappingService, TeamService teamService, ManagerService managerService,
                          ClubService clubService, LeagueService leagueService) {
        this.mappingService = mappingService;
        this.teamService = teamService;
        this.managerService = managerService;
        this.clubService = clubService;
        this.leagueService = leagueService;
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
        return null;

        // TODO
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
        return null;

        //TODO
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
    public ResponseEntity<GetTeamInfoDto> updateTeam(@PathVariable final Long id,
                                                     @Valid @RequestBody final AddTeamInfoDto updateTeamDto) {
        return null;

        // TODO
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
    public void deleteTeamById(@PathVariable Long id) {

        // TODO

    }
}