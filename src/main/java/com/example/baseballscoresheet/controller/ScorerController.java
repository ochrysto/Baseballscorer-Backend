package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.model.dto.scorer.GetScorerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/scorer")
public class ScorerController {

    /*
    // Endpunkt zum Speichern eines neuen Scorers
    @Operation(summary = "saves a new scorer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created scorer",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetScorerDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<GetScorerDto> createScorer(@RequestBody @Valid AddScorerDto newScorer) {
        return null;
    }
*/

    // Endpunkt, um alle existierenden Scorer abzurufen
    @Operation(summary = "retrieve all existing scorers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "scorers found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetScorerDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "scorers not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetScorerDto>> findAllScorers() {
        return null;
    }

    // Endpunkt, um Informationen zu einem bestimmten Scorer abzurufen
    @Operation(summary = "retrieve all information of a specific scorer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "scorer found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetScorerDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "scorer not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetScorerDto> findScorerById(@PathVariable Long id) {
        return null;
    }

    /*
    // Endpunkt zum Updaten eines existierenden Scorers
    @Operation(summary = "updates a existing scorer",
            description = "scorer must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "scorer found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetScorerDto.class))}),
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "scorer not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetScorerDto> updateScorer(@PathVariable final Long id,
                                                     @Valid @RequestBody final UpdateScorerDto updateScorerDto) {
        return null;
    }
    */

    // Endpunkt, um einen existierenden Scorer zu löschen
    @Operation(summary = "deletes scorer by id",
            description = "scorer must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "scorer not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @RolesAllowed("user")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteScorerById(@PathVariable Long id) {

    }
}
