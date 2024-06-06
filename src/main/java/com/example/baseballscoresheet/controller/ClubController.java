package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.entities.ClubEntity;
import com.example.baseballscoresheet.model.dtos.club.ClubGetDto;
import com.example.baseballscoresheet.services.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {

    private final ClubService clubService;
    private final MappingService mappingService;

    public ClubController(ClubService clubService, MappingService mappingService) {
        this.clubService = clubService;
        this.mappingService = mappingService;
    }

    // Endpoint to retrieve all existing clubs
    @Operation(summary = "retrieve all existing clubs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "clubs found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClubGetDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "clubs not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<ClubGetDto>> findAllClubs() {
        List<ClubEntity> allClubEntities = clubService.readAll();
        List<ClubGetDto> allClubDtos = new LinkedList<>();
        for (ClubEntity clubEntity : allClubEntities) {
            allClubDtos.add(mappingService.mapClubEntityToGetClubDto(clubEntity));
        }
        return new ResponseEntity<>(allClubDtos, HttpStatus.OK);
    }
}
