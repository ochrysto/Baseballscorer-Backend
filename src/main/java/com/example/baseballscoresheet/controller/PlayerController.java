package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.Utility;
import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.PlayerEntity;
import com.example.baseballscoresheet.model.dto.player.AddPlayerInfoDto;
import com.example.baseballscoresheet.model.dto.player.GetPlayerInfoDto;
import com.example.baseballscoresheet.services.PlayerService;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final MappingService mappingService;
    private final PlayerService playerService;

    public PlayerController(MappingService mappingService, PlayerService playerService) {
        this.mappingService = mappingService;
        this.playerService = playerService;
    }

    // Endpoint for saving a new player
    @Operation(summary = "saves a new player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlayerInfoDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<GetPlayerInfoDto> createPlayer(@RequestBody @Valid AddPlayerInfoDto newPlayer) {
        // mapping AddPlayerInfoDto object to PlayerEntity object
        PlayerEntity playerEntity = this.mappingService.mapAddPlayerInfoDtoToPlayerEntity(newPlayer);
        // saving PlayerEntity object in database
        playerEntity = this.playerService.createPlayer(playerEntity);
        // mapping saved PlayerEntity object to GetPlayerInfoDto object
        GetPlayerInfoDto addedPlayer = this.mappingService.mapPlayerEntityToGetPlayerInfoDto(playerEntity);
        // returning mapped GetPlayerInfoDto object
        return new ResponseEntity<>(addedPlayer, HttpStatus.CREATED);
    }

    // Endpoint to retrieve all existing players
    @Operation(summary = "retrieve all existing players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "players found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlayerInfoDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "players not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @GetMapping
    @RolesAllowed("user")
    public ResponseEntity<List<GetPlayerInfoDto>> findAllPlayers() {
        // creates list with all player objects from database
        List<PlayerEntity> playerEntities = this.playerService.findAllPlayers();
        List<GetPlayerInfoDto> playerDtos = new LinkedList<>();
        // maps each PlayerEntity object to a GetPlayerInfoDto object, adds each GetPlayerInfoDto object to list
        for (PlayerEntity playerEntity : playerEntities) {
            playerDtos.add(this.mappingService.mapPlayerEntityToGetPlayerInfoDto(playerEntity));
        }
        // returns list of GetPlayerInfoDto objects
        return new ResponseEntity<>(playerDtos, HttpStatus.OK);
    }

    // Endpoint to retrieve information about a specific player
    @Operation(summary = "retrieve all information of a specific player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "player found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlayerInfoDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "player not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GetPlayerInfoDto> findPlayerById(@PathVariable Long id) {
        PlayerEntity playerEntity = Utility.returnPlayerIfExists(id);
        GetPlayerInfoDto getPlayerInfoDto = this.mappingService.mapPlayerEntityToGetPlayerInfoDto(playerEntity);
        return new ResponseEntity<>(getPlayerInfoDto, HttpStatus.OK);
    }

    // Endpoint for updating an existing player
    @Operation(summary = "updates a existing player",
            description = "player must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "player found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlayerInfoDto.class))}),
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
    public ResponseEntity<GetPlayerInfoDto> updatePlayer(@PathVariable final Long id,
                                                         @Valid @RequestBody final AddPlayerInfoDto addPlayerInfoDto) {
        GetPlayerInfoDto updatedPlayerDto;
        // the passed AddPlayerInfoDto and the id are mapped to a PlayerEntity object
        PlayerEntity updatedPlayerEntity = this.mappingService.mapAddPlayerInfoDtoToPlayerEntity(addPlayerInfoDto);
        updatedPlayerEntity.setId(id);
        // the mapped PlayerEntity object is passed on to the PlayerService
        // the service checks whether the PlayerEntity object that is to be updated in the database actually exists in the database
        // if not, a ResourceNotFoundException is thrown
        // if the corresponding PlayerEntity object is found, its attribute values are updated with the attribute values of the transferred PlayerEntity object
        // the updated PlayerEntity object is stored in the database
        updatedPlayerEntity = this.playerService.update(updatedPlayerEntity);
        // the mapped PlayerEntity object is mapped to a GetPlayerInfoDto object and returned
        updatedPlayerDto = this.mappingService.mapPlayerEntityToGetPlayerInfoDto(updatedPlayerEntity);
        return new ResponseEntity<>(updatedPlayerDto, HttpStatus.CREATED);
    }

    //Endpoint to delete an existing player
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
    public ResponseEntity<Object> deletePlayerById(@PathVariable Long id) {
        // check whether the id is present in the database
        if (playerService.findPlayerById(id).isPresent()) {
            // if yes, then the data record with the id is deleted
            this.playerService.delete(id);
        } else {
            // if not, then a ResourceNotFoundException is thrown
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player with id: " + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}