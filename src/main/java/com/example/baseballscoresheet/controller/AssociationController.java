package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.model.dto.association.GetAssociationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/association")
public class AssociationController {

    /*
    // Endpunkt zum Speichern einer neuen Association
    @Operation(summary = "saves a new association")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created association",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetAssociationDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<GetAssociationDto> createAssociation(@RequestBody @Valid AddAssociationDto newAssociation) {
        return null;
    }
    */

    // Endpunkt, um alle existierenden Associations abzurufen
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
        return null;
    }

    // Endpunkt, um Informationen zu einer bestimmten Association abzurufen
    @Operation(summary = "retrieve all information of a specific association")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "association found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetAssociationDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "association not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetAssociationDto> findAssociationById(@PathVariable Long id) {
        return null;
    }

    /*
    // Endpunkt zum Updaten einer existierenden Association
    @Operation(summary = "updates a existing association",
            description = "association must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "association found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetAssociationDto.class))}),
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "association not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetAssociationDto> updateAssociation(@PathVariable final Long id,
                                                               @Valid @RequestBody final UpdateAssociationDto updateAssociationDto) {
        return null;
    }

    // Endpunkt, um eine existierende Association zu löschen
    @Operation(summary = "deletes association by id",
            description = "association must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "association not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @RolesAllowed("user")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAssociationById(@PathVariable Long id) {

    }
    */
}