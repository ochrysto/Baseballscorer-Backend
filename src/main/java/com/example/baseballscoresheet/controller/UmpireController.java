package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.model.dto.umpire.GetUmpireDto;
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
@RequestMapping("/umpire")
public class UmpireController {

    /*
    // Endpunkt zum Speichern eines neuen Umpires
    @Operation(summary = "saves a new umpire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created umpire",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUmpireDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<GetUmpireDto> createUmpire(@RequestBody @Valid AddUmpireDto newUmpire) {
        return null;
    }
    */

    // Endpunkt, um alle existierenden Umpires abzurufen
    @Operation(summary = "retrieve all existing umpires")
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
        return null;
    }

    // Endpunkt, um Informationen zu einem bestimmten Umpire abzurufen
    @Operation(summary = "retrieve all information of a specific umpire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "umpire found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUmpireDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "umpire not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetUmpireDto> findUmpireById(@PathVariable Long id) {
        return null;
    }

    /*
    // Endpunkt zum Updaten eines existierenden Umpires
    @Operation(summary = "updates a existing umpire",
            description = "umpire must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "umpire found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUmpireDto.class))}),
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "umpire not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetUmpireDto> updateUmpire(@PathVariable final Long id,
                                                     @Valid @RequestBody final UpdateUmpireDto updateUmpireDto) {
        return null;
    }
    */

    // Endpunkt, um einen existierenden Umpire zu löschen
    @Operation(summary = "deletes umpire by id",
            description = "umpire must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "umpire not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @RolesAllowed("user")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUmpireById(@PathVariable Long id) {

    }
}
