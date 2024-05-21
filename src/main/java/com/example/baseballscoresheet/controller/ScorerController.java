package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.entities.ScorerEntity;
import com.example.baseballscoresheet.model.dtos.scorer.GetScorerDto;
import com.example.baseballscoresheet.services.ScorerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/scorer")
public class ScorerController {

    private final ScorerService scorerService;
    private final MappingService mappingService;

    @Autowired
    public ScorerController(ScorerService scorerService, MappingService mappingService) {
        this.scorerService = scorerService;
        this.mappingService = mappingService;
    }

    // Endpoint for getting all official scorers
    @Operation(summary = "retrieve all official scorers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "scorers found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetScorerDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "scorers not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetScorerDto>> findAllScorers() {
        List<ScorerEntity> allScorersEntities = scorerService.readAll();
        List<GetScorerDto> allScorersDtos = new ArrayList<>();
        for (ScorerEntity scorerEntity : allScorersEntities) {
            allScorersDtos.add(mappingService.mapScorerEntityToGetScorerDto(scorerEntity));
        }
        return new ResponseEntity<>(allScorersDtos, HttpStatus.OK);
    }
}