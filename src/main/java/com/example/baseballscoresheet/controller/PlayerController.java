package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.model.dto.player.GetPlayerDto;
import com.example.baseballscoresheet.model.dto.player.UpdatePlayerDto;
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
@RequestMapping("/player")
public class PlayerController {

    /*
    // Endpunkt zum Speichern eines neuen Players
    @Operation(summary = "saves a new player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlayerDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<GetPlayerDto> createPlayer(@RequestBody @Valid AddPlayerDto newPlayer) {
        return null;
    }
    */

    // Endpunkt, um alle existierenden Player abzurufen
    @Operation(summary = "retrieve all existing players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "players found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlayerDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "players not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetPlayerDto>> findAllPlayers() {
        return null;
    }


    // Endpunkt, um Informationen zu einem bestimmten Player abzurufen
    @Operation(summary = "retrieve all information of a specific player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "player found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlayerDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "player not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetPlayerDto> findPlayerById(@PathVariable Long id) {
        return null;
    }

    // Endpunkt zum Updaten eines existierenden Players
    @Operation(summary = "updates a existing player",
            description = "player must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "player found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlayerDto.class))}),
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "player not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetPlayerDto> updatePlayer(@PathVariable final Long id,
                                                     @Valid @RequestBody final UpdatePlayerDto updatePlayerDto) {
        return null;
    }

    // Endpunkt, um einen existierenden Player zu löschen
    @Operation(summary = "deletes player by id",
            description = "player must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "player not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @RolesAllowed("user")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePlayerById(@PathVariable Long id) {

    }
}
