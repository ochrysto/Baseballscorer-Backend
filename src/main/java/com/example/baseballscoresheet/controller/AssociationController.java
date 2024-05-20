package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.AssociationEntity;
import com.example.baseballscoresheet.model.LeagueEntity;
import com.example.baseballscoresheet.model.dto.association.GetAssociationDto;
import com.example.baseballscoresheet.model.dto.league.GetLeagueDto;
import com.example.baseballscoresheet.services.AssociationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/association")
public class AssociationController {

    private final AssociationService associationService;
    private final MappingService mappingService;

    public AssociationController(AssociationService associationService, MappingService mappingService) {
        this.associationService = associationService;
        this.mappingService = mappingService;
    }

    // Endpoint for getting all associations
    @Operation(summary = "retrieve all existing associations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "associations found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetAssociationDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "associations not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetAssociationDto>> findAllAssociations() {
        List<AssociationEntity> allAssociationEntities = associationService.readAll();
        List<GetAssociationDto> allAssociationDos = new ArrayList<>();
        for (AssociationEntity associationEntity : allAssociationEntities) {
            allAssociationDos.add(mappingService.mapAssociationEntityToGetAssociationDto(associationEntity));
        }
        return new ResponseEntity<>(allAssociationDos, HttpStatus.OK);
    }
}