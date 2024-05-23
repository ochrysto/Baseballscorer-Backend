package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.dtos.game.AddGameDto;
import com.example.baseballscoresheet.model.dtos.game.GetEndedGameDto;
import com.example.baseballscoresheet.model.dtos.game.GetGameDto;
import com.example.baseballscoresheet.model.dtos.game.UpdateGameDto;
import com.example.baseballscoresheet.model.dtos.team.AddTeamInfoDto;
import com.example.baseballscoresheet.model.dtos.team.GetTeamInfoDto;
import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.services.*;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final MappingService mappingService;
    private final AssociationService associationService;
    private final LeagueService leagueService;
    private final UmpireService umpireService;
    private final ScorerService scorerService;
    private final TeamService teamService;
    private final GameUmpireService gameUmpireService;

    public GameController(GameService gameService, MappingService mappingService, AssociationService associationService, LeagueService leagueService, UmpireService umpireService, ScorerService scorerService, TeamService teamService, GameUmpireService gameUmpireService) {
        this.gameService = gameService;
        this.mappingService = mappingService;
        this.associationService = associationService;
        this.leagueService = leagueService;
        this.umpireService = umpireService;
        this.scorerService = scorerService;
        this.teamService = teamService;
        this.gameUmpireService = gameUmpireService;
    }

    // Endpoint for saving a new game
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
                    content = @Content)})
    @PostMapping
    @RolesAllowed("user")
    public ResponseEntity<GetGameDto> createGame(@RequestBody @Valid AddGameDto addGameDto) {
        AssociationEntity associationEntity = this.associationService.findById(addGameDto.getAssociationId());
        LeagueEntity leagueEntity = this.leagueService.getLeagueById(addGameDto.getLeagueId());
        ScorerEntity scorerEntity = this.scorerService.findById(addGameDto.getScorerId());
        TeamEntity hostTeam = this.teamService.findTeamById(addGameDto.getHostTeamId());
        TeamEntity guestTeam = this.teamService.findTeamById(addGameDto.getGuestTeamId());
        GameEntity gameEntity;

        // maps transferred umpires to UmpireEntities and adds them to a list
        List<UmpireEntity> umpires = new ArrayList<>();
        for (Long umpireId : addGameDto.getUmpireIdsList()) {
            umpires.add(this.umpireService.findById(umpireId));
        }

        // maps each UmpireEntity from the list to a GameUmpireEntity and saves it in db
        // adds these to a new list
        List<GameUmpireEntity> gameUmpireEntities = new ArrayList<>();
        if (umpires.size() == 2) {
            if (umpires.getFirst().equals(umpires.get(1))) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "First und second umpire must not be the same");
            } else {
                // saving game
                if (!hostTeam.equals(guestTeam)) {
                    gameEntity = this.mappingService.mapInformationToGameEntity(addGameDto, associationEntity, leagueEntity, hostTeam, guestTeam, scorerEntity);
                    gameEntity = this.gameService.createGame(gameEntity);
                } else {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Host team and guest team must not be the same");
                }
                for (UmpireEntity umpire : umpires) {
                    GameUmpireEntity gameUmpire = this.mappingService.mapUmpireEntityToGameUmpireEntity(gameEntity, umpire);
                    gameUmpireEntities.add(gameUmpire);
                    this.gameUmpireService.save(gameUmpire);
                }
            }
        } else {
            // saving game
            if (!hostTeam.equals(guestTeam)) {
                gameEntity = this.mappingService.mapInformationToGameEntity(addGameDto, associationEntity, leagueEntity, hostTeam, guestTeam, scorerEntity);
                gameEntity = this.gameService.createGame(gameEntity);
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Host team and guest team must not be the same");
            }
            GameUmpireEntity gameUmpire = this.mappingService.mapUmpireEntityToGameUmpireEntity(gameEntity, umpires.getFirst());
            gameUmpireEntities.add(gameUmpire);
            this.gameUmpireService.save(gameUmpire);
        }
        GetGameDto addedGame = this.mappingService.mapToGetGameDto(gameEntity, gameUmpireEntities);
        return new ResponseEntity<>(addedGame, HttpStatus.CREATED);
    }

    // Endpoint for updating an existing game after it is finished
    @Operation(summary = "updates an existing game after it is finished",
            description = "game must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "game found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetEndedGameDto.class))}),
            @ApiResponse(responseCode = "204", description = "no content"),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "game not found"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content)
    })
    @PutMapping("/{gameNr}")
    @RolesAllowed("user")
    public ResponseEntity<GetGameDto> updateGameInfo(@PathVariable final Integer gameNr,
                                                     @Valid @RequestBody final UpdateGameDto updateGameDto) {

        GameEntity updatedGameEntity = this.mappingService.mapUpdateGameDtoToGameEntity(updateGameDto);
        GameEntity foundGameEntity = this.gameService.findGameByGameNr(gameNr);
        updatedGameEntity.setId(foundGameEntity.getId());
        updatedGameEntity = this.gameService.update(updatedGameEntity);

        List<GameUmpireEntity> gameUmpireEntities = gameUmpireService.findAllByGameId(foundGameEntity.getId());

        // TODO GetGameDto Struktur anpassen
        GetGameDto updatedGameDto = this.mappingService.mapToGetGameDto(updatedGameEntity, gameUmpireEntities);
        return new ResponseEntity<>(updatedGameDto, HttpStatus.CREATED);
    }
}