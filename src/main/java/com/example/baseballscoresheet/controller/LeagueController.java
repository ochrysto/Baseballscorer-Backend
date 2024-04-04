package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.model.dto.league.GetLeagueDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/league")
public class LeagueController {

    /*
    // Endpunkt zum Speichern einer neuen League
    @Operation(summary = "saves a new league")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created league",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetLeagueDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<GetLeagueDto> createLeague(@RequestBody @Valid AddLeagueDto newLeague) {
        return null;
    }
     */

    // Endpunkt, um alle existierenden Leagues abzurufen
    @Operation(summary = "retrieve all existing leagues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "leagues found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetLeagueDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "leagues not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetLeagueDto>> findAllLeagues() {
        return null;
    }

    // Endpunkt, um Informationen zu einer bestimmten League abzurufen
    @Operation(summary = "retrieve all information of a specific league")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "league found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetLeagueDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "league not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetLeagueDto> findLeagueById(@PathVariable Long id) {
        return null;
    }

    /*
    // Endpunkt zum Updaten einer existierenden League
    @Operation(summary = "updates a existing league",
            description = "league must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "league found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetLeagueDto.class))}),
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "league not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetLeagueDto> updateLeague(@PathVariable final Long id,
                                                     @Valid @RequestBody final UpdateLeagueDto updateLeagueDto) {
        return null;
    }

    // Endpunkt, um ein existierende League zu löschen
    @Operation(summary = "deletes league by id",
            description = "league must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "league not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @RolesAllowed("user")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteLeagueById(@PathVariable Long id) {

    }
   */
}
