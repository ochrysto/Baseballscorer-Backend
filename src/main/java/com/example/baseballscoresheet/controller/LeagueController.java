package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.entities.LeagueEntity;
import com.example.baseballscoresheet.model.dtos.league.GetLeagueDto;
import com.example.baseballscoresheet.services.LeagueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/league")
public class LeagueController {

    private final LeagueService leagueService;
    private final MappingService mappingService;

    public LeagueController(LeagueService leagueService, MappingService mappingService) {
        this.leagueService = leagueService;
        this.mappingService = mappingService;
    }

    // Endpoint for getting all leagues
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
        List<LeagueEntity> allLeagueEntities = leagueService.readAll();
        List<GetLeagueDto> allLeagueDtos = new ArrayList<>();
        for (LeagueEntity leagueEntity : allLeagueEntities) {
            allLeagueDtos.add(mappingService.mapLeagueEntityToGetLeagueDto(leagueEntity));
        }
        return new ResponseEntity<>(allLeagueDtos, HttpStatus.OK);
    }
}
