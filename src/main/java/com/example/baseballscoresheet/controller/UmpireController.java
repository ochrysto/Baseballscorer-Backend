package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.entities.UmpireEntity;
import com.example.baseballscoresheet.model.dtos.umpire.GetUmpireDto;
import com.example.baseballscoresheet.services.UmpireService;
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
@RequestMapping("/umpire")
public class UmpireController {

    private final UmpireService umpireService;
    private final MappingService mappingService;

    public UmpireController(UmpireService umpireService, MappingService mappingService) {
        this.umpireService = umpireService;
        this.mappingService = mappingService;
    }

    // Endpoint for getting all official umpires
    @Operation(summary = "retrieve all official umpires")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "umpires found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUmpireDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "umpires not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetUmpireDto>> findAllUmpires() {
        List<UmpireEntity> allUmpireEntities = umpireService.readAll();
        List<GetUmpireDto> allUmpireDtos = new ArrayList<>();
        for (UmpireEntity umpireEntity : allUmpireEntities) {
            allUmpireDtos.add(mappingService.mapUmpireEntityToGetUmpireDto(umpireEntity));
        }
        return new ResponseEntity<>(allUmpireDtos, HttpStatus.OK);
    }
}