package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.TeamEntity;
import com.example.baseballscoresheet.model.dto.team.AddTeamInfoDto;
import com.example.baseballscoresheet.model.dto.team.GetTeamInfoDto;
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


@RestController
@RequestMapping("/team")
public class TeamController {

    private final MappingService mappingService;
    private final TeamService teamService;

    @Autowired
    public TeamController(MappingService mappingService, TeamService teamService) {
        this.mappingService = mappingService;
        this.teamService = teamService;
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
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<GetTeamInfoDto> createTeam(@RequestBody @Valid AddTeamInfoDto addTeamInfoDto) {
        TeamEntity teamEntity = this.mappingService.mapAddTeamDtoToTeamEntity(addTeamInfoDto);
        teamEntity = this.teamService.createTeam(teamEntity);
        GetTeamInfoDto request = this.mappingService.mapTeamToGetTeamInfoDto(teamEntity);
        return new ResponseEntity<>(request, HttpStatus.OK);
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
