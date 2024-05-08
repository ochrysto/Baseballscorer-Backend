package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.model.dto.game.AddGameDto;
import com.example.baseballscoresheet.model.dto.game.GetGameDto;
import com.example.baseballscoresheet.model.dto.game.UpdateGameDto;
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
@RequestMapping("/game")
public class GameController {

    // Endpunkt zum Speichern eines neuen Games
    @Operation(summary = "saves a new game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created game",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetGameDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<GetGameDto> createGame(@RequestBody @Valid AddGameDto newGame) {
        return null;
    }

    // Endpunkt, um alle existierenden Games abzurufen
    @Operation(summary = "retrieve all existing games")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "games found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetGameDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "games not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetGameDto>> findAllGames() {
        return null;
    }

    // Endpunkt zum Updaten eines existierenden Games
    @Operation(summary = "updates a existing game",
            description = "game must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "game found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetGameDto.class))}),
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "game not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetGameDto> updateGame(@PathVariable final Long id,
                                                 @Valid @RequestBody final UpdateGameDto updateGameDto) {
        return null;
    }

    // Endpunkt, um ein existierendes Game zu löschen
    @Operation(summary = "deletes game by id",
            description = "game must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "game not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @RolesAllowed("user")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteGameById(@PathVariable Long id) {

    }
}
