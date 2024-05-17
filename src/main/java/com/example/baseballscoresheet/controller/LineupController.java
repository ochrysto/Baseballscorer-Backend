package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.LineupEntity;
import com.example.baseballscoresheet.model.LineupTeamPlayerEntity;
import com.example.baseballscoresheet.model.dto.lineup.AddLineupDto;
import com.example.baseballscoresheet.model.dto.lineup.GetLineupDto;
import com.example.baseballscoresheet.services.LineupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lineup")
public class LineupController {

    private final MappingService mappingService;
    private final LineupService lineupService;

    public LineupController(MappingService mappingService, LineupService lineupService) {
        this.mappingService = mappingService;
        this.lineupService = lineupService;
    }

    // Endpoint for saving lineups
    @Operation(summary = "saves lineup(s)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created lineup(s)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetLineupDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<List<GetLineupDto>> createLineups(@RequestBody @Valid List<AddLineupDto> newLineups) {
        List<GetLineupDto> addedLineup = new ArrayList<>();

        // müssen beide gespeichert werden
        LineupEntity lineupEntity;
        LineupTeamPlayerEntity lineupTeamPlayerEntity = new LineupTeamPlayerEntity();

        // beide lineups werden gemappt und gespeichert
        for (AddLineupDto addLineupDto : newLineups) {
            lineupEntity = this.mappingService.mapAddLineupDtoToLineupEntity(addLineupDto);
            this.lineupService.saveLineup(lineupEntity);
        }
        return new ResponseEntity<>(addedLineup, HttpStatus.CREATED);
    }
}
