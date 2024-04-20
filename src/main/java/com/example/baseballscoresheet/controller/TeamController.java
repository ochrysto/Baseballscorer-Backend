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
import org.springframework.beans.factory.annotation.Autowired;
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

    // Endpunkt zum Speichern eines neuen Teams
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

        // sucht anhand der übergebenen managerId nach Manager in DB
        Optional<ManagerEntity> managerOptional = managerService.getManagerById(addTeamInfoDto.getManagerId());
        if (managerOptional.isPresent()) {
            // fügt ManagerEntity bei gefundenen DB-Eintrag zur TeamEntity hinzu
            managerEntity = managerOptional.get();
        } else {
            // wirft Exception, wenn kein passender Manager in DB gefunden wurde
            throw new RessourceNotFoundException("Manager with id " + addTeamInfoDto.getManagerId() + " was not found.");
        }

        // sucht anhand der übergebenen clubId nach Club in DBe
        Optional<ClubEntity> clubOptional = clubService.getClubById(addTeamInfoDto.getClubId());
        if (clubOptional.isPresent()) {
            // fügt ClubEntity bei gefundenen DB-Eintrag zur TeamEntity hinzu
            clubEntity = clubOptional.get();
        } else {
            // wirft Exception, wenn kein passender Manager in DB gefunden wurde
            throw new RessourceNotFoundException("Club with id " + addTeamInfoDto.getClubId() + "was not found.");
        }


        // sucht anhand der übergebenen managerId nach Manager in DB
        Optional<LeagueEntity> leagueOptional = leagueService.getLeagueById(addTeamInfoDto.getLeagueId());
        if (leagueOptional.isPresent()) {
            // fügt LeagueEntity bei gefundenen DB-Eintrag zur TeamEntity hinzu
            leagueEntity = leagueOptional.get();
        } else {
            // wirft Exception, wenn kein passender Manager in DB gefunden wurde
            throw new RessourceNotFoundException("League with id " + addTeamInfoDto.getLeagueId() + "was not found.");
        }

        // mappt TeamDto auf TeamEntity und speichert sie in Datenbank
        TeamEntity teamEntity = this.mappingService.mapAddTeamInfoDtoToTeamEntity(
                addTeamInfoDto, managerEntity, clubEntity, leagueEntity);
        teamEntity = this.teamService.createTeam(teamEntity);

        GetTeamInfoDto addedTeam = this.mappingService.mapTeamToGetTeamInfoDto(teamEntity);

        return new ResponseEntity<>(addedTeam, HttpStatus.CREATED);
    }

    // Endpunkt, um alle existierenden Teams abzurufen
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
    }

    // Endpunkt, um Informationen zu einem bestimmten Team abzurufen
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
    }

    // Endpunkt zum Updaten eines existierenden Teams
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
    }

    // Endpunkt, um ein existierendes Team zu löschen
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
    }
}