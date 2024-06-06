package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.dtos.club.ClubGetDto;
import com.example.baseballscoresheet.model.dtos.position.PositionGetDto;
import com.example.baseballscoresheet.model.entities.PositionEntity;
import com.example.baseballscoresheet.services.PositionService;
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
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;
    private final MappingService mappingService;

    public PositionController(PositionService positionService, MappingService mappingService) {
        this.positionService = positionService;
        this.mappingService = mappingService;
    }

    // Endpoint to retrieve all existing clubs
    @Operation(summary = "retrieve all available positions")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "positions found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClubGetDto.class))}), @ApiResponse(responseCode = "401", description = "not authorized", content = @Content), @ApiResponse(responseCode = "404", description = "clubs not found", content = @Content), @ApiResponse(responseCode = "500", description = "server error", content = @Content),})
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<PositionGetDto>> getAllPositions() {
        List<PositionEntity> allPositionsEntities = this.positionService.findAll();
        List<PositionGetDto> allPositionsDtos = new ArrayList<>();

        for (PositionEntity positionEntity : allPositionsEntities) {
            allPositionsDtos.add(this.mappingService.mapPositionEntityToGetPositionDto(positionEntity));
        }
        return new ResponseEntity<>(allPositionsDtos, HttpStatus.OK);
    }
}