package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.ManagerEntity;
import com.example.baseballscoresheet.model.dto.manager.GetManagerDto;
import com.example.baseballscoresheet.services.ManagerService;
import com.example.baseballscoresheet.services.TeamService;
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
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final MappingService mappingService;
    private final TeamService teamService;

    public ManagerController(ManagerService managerService, MappingService mappingService, TeamService teamService) {
        this.managerService = managerService;
        this.mappingService = mappingService;
        this.teamService = teamService;
    }
    /*
     // Endpoint to retrieve all existing managers
     @Operation(summary = "retrieve all existing managers")
     @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "managers found",
     content = {@Content(mediaType = "application/json",
     schema = @Schema(implementation = GetManagerDto.class))}),
     @ApiResponse(responseCode = "401", description = "not authorized",
     content = @Content),
     @ApiResponse(responseCode = "404", description = "managers not found",
     content = @Content),
     @ApiResponse(responseCode = "500", description = "server error",
     content = @Content),
     })
     @GetMapping
     @RolesAllowed("user") public ResponseEntity<List<GetManagerDto>> findAllManagers() {
     List<ManagerEntity> allManagerEntities = managerService.readAll();
     List<GetManagerDto> allManagerDos = new LinkedList<>();

     for (ManagerEntity manager : allManagerEntities) {
     allManagerDos.add(mappingService.mapManagerEntityToGetManagerDto(manager));
     }
     return new ResponseEntity<>(allManagerDos, HttpStatus.OK);
     }
     */

    // Endpoint to retrieve all managers who are not assigned to a team
    @Operation(summary = "retrieve all information of a specific manager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "manager found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetManagerDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "manager not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetManagerDto>> findAllAvailableManagers() {
        List<ManagerEntity> allManagers = managerService.readAll();
        List<ManagerEntity> listOfAllManagersInTeams = teamService.findAllManagersInTeams();
        List<GetManagerDto> allAvailableManagers = new ArrayList<>();

        // checks which managers are not yet assigned to a team
        for (ManagerEntity manager : allManagers) {
            if (!listOfAllManagersInTeams.contains(manager)) {
                allAvailableManagers.add(mappingService.mapManagerEntityToGetManagerDto(manager));
            }
        }
        return new ResponseEntity<>(allAvailableManagers, HttpStatus.OK);
    }

    // Endpoint to retrieve information about a specific manager
    @Operation(summary = "retrieve all information of a specific manager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "manager found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetManagerDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "manager not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetManagerDto> findManagerById(@PathVariable Long id) {
        return null;
    }

    // Endpunkt, um alle existierenden Managers abzurufen
    /*@Operation(summary = "retrieve all existing managers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "managers found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetManagerDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "managers not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetManagerDto>> findAllManagers() {
        List<ManagerEntity> allManagerEntities = managerService.readAll();
        List<GetManagerDto> allManagerDos = new LinkedList<>();

        for (ManagerEntity manager : allManagerEntities) {
            allManagerDos.add(mappingService.mapManagerEntityToGetManagerDto(manager));
        }
        return new ResponseEntity<>(allManagerDos, HttpStatus.OK);
    }*/
}