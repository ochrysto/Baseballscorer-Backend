package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.ManagerEntity;
import com.example.baseballscoresheet.model.dto.manager.GetManagerDto;
import com.example.baseballscoresheet.services.ManagerService;
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
@RequestMapping(value="/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final MappingService mappingService;

    public ManagerController(ManagerService managerService, MappingService mappingService) {
        this.managerService = managerService;
        this.mappingService = mappingService;
    }

    /*
    // Endpunkt zum Speichern eines neuen Managers
    @Operation(summary = "saves a new manager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created manager",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetManagerDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<GetManagerDto> createManager(@RequestBody @Valid AddManagerDto newManager) {
        return null;
    }
    /*
     */

    // Endpunkt, um alle existierenden Managers abzurufen
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
    @RolesAllowed("user")
    public ResponseEntity<List<GetManagerDto>> findAllManagers() {
        List<ManagerEntity> allManagerEntities = managerService.readAll();
        List<GetManagerDto> allManagerDtos = new LinkedList<>();

        for (ManagerEntity manager : allManagerEntities) {
            allManagerDtos.add(mappingService.mapManagerEntityToGetManagerDto(manager));
        }
        return new ResponseEntity<>(allManagerDtos, HttpStatus.OK);
    }

    // Endpunkt, um Informationen zu einem bestimmten Manager abzurufen
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

    /*
    // Endpunkt zum Updaten eines existierenden Managers
    @Operation(summary = "updates a existing manager",
            description = "manager must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "manager found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetManagerDto.class))}),
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "manager not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetManagerDto> updateManager(@PathVariable final Long id,
                                                       @Valid @RequestBody final UpdateManagerDto updateManagerDto) {
        return null;
    }
    */

    // Endpunkt, um einen existierenden Manager zu löschen
    @Operation(summary = "deletes manager by id",
            description = "manager must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "manager not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @RolesAllowed("user")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteManagerById(@PathVariable Long id) {

    }
}
