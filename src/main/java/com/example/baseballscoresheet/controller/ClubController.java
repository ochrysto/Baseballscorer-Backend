package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.model.dto.club.GetClubDto;
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
@RequestMapping("/club")
public class ClubController {

    /*
    // Endpunkt zum Speichern eines neuen Clubs
    @Operation(summary = "saves a new club")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created club",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetClubDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<GetClubDto> createClub(@RequestBody @Valid AddClubDto newClub) {
        return null;
    }
*/

    // Endpunkt, um alle existierenden Clubs abzurufen
    @Operation(summary = "retrieve all existing clubs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "clubs found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetClubDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "clubs not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetClubDto>> findAllClubs() {
        return null;
    }

    // Endpunkt, um Informationen zu einem bestimmten Club abzurufen
    @Operation(summary = "retrieve all information of a specific club")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "club found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetClubDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "club not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetClubDto> findClubById(@PathVariable Long id) {
        return null;
    }

    /*
    // Endpunkt zum Updaten eines existierenden Clubs
    @Operation(summary = "updates a existing club",
            description = "club must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "club found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetClubDto.class))}),
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "club not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetClubDto> updateClub(@PathVariable final Long id,
                                                 @Valid @RequestBody final UpdateClubDto updateClubDto) {
        return null;
    }
    */

    // Endpunkt, um einen existierenden Club zu löschen
    @Operation(summary = "deletes club by id",
            description = "club must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "club not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @RolesAllowed("user")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteClubById(@PathVariable Long id) {

    }
}
