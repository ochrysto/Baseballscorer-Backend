package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.exceptionHandling.BadRequestError;
import com.example.baseballscoresheet.exceptionHandling.DoubleInputException;
import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.dtos.game.GameAddDto;
import com.example.baseballscoresheet.model.dtos.game.GameFinishedGetDto;
import com.example.baseballscoresheet.model.dtos.game.GameGetDto;
import com.example.baseballscoresheet.model.dtos.game.GamePutDto;
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
    private final GameStateService gameStateService;

    public GameController(GameService gameService, MappingService mappingService, AssociationService associationService, LeagueService leagueService, UmpireService umpireService, ScorerService scorerService, TeamService teamService, GameUmpireService gameUmpireService, GameStateService gameStateService) {
        this.gameService = gameService;
        this.mappingService = mappingService;
        this.associationService = associationService;
        this.leagueService = leagueService;
        this.umpireService = umpireService;
        this.scorerService = scorerService;
        this.teamService = teamService;
        this.gameUmpireService = gameUmpireService;
        this.gameStateService = gameStateService;
    }

    // Endpoint for saving a new game
    @Operation(summary = "saves a new game")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "created game", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameGetDto.class))}), @ApiResponse(responseCode = "400", description = "invalid JSON posted", content = @Content), @ApiResponse(responseCode = "401", description = "not authorized", content = @Content), @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @PostMapping
    @RolesAllowed("user")
    public ResponseEntity<GameGetDto> createGame(@RequestBody @Valid GameAddDto gameAddDto) {
        AssociationEntity associationEntity = this.associationService.findById(gameAddDto.getAssociationId());
        LeagueEntity leagueEntity = this.leagueService.getLeagueById(gameAddDto.getLeagueId());
        ScorerEntity scorerEntity = this.scorerService.findById(gameAddDto.getScorerId());
        TeamEntity hostTeam = this.teamService.findTeamById(gameAddDto.getHostTeamId());
        TeamEntity guestTeam = this.teamService.findTeamById(gameAddDto.getGuestTeamId());
        GameEntity gameEntity;

        // maps transferred umpires to UmpireEntities and adds them to a list
        List<UmpireEntity> umpires = new ArrayList<>();
        for (Long umpireId : gameAddDto.getUmpireIdsList()) {
            umpires.add(this.umpireService.findById(umpireId));
        }

        // maps each UmpireEntity from the list to a GameUmpireEntity and saves it in db
        // adds these to a new list
        List<GameUmpireEntity> gameUmpireEntities = new ArrayList<>();
        if (umpires.size() == 2) {
            if (umpires.get(0).equals(umpires.get(1))) {
                throw new DoubleInputException("First und second umpire must not be the same");
            } else {
                // saving game
                if (!hostTeam.equals(guestTeam)) {
                    gameEntity = this.mappingService.mapToGameEntity(gameAddDto, associationEntity, leagueEntity, hostTeam, guestTeam, scorerEntity);
                    gameEntity = this.gameService.createGame(gameEntity);
                } else {
                    throw new DoubleInputException("Host team and guest team must not be the same");
                }
                for (UmpireEntity umpire : umpires) {
                    GameUmpireEntity gameUmpire = this.mappingService.mapToGameUmpireEntity(gameEntity, umpire);
                    gameUmpireEntities.add(gameUmpire);
                    this.gameUmpireService.save(gameUmpire);
                }
            }
        } else if (umpires.size() == 1) {
            // saving game
            if (!hostTeam.equals(guestTeam)) {
                gameEntity = this.mappingService.mapToGameEntity(gameAddDto, associationEntity, leagueEntity, hostTeam, guestTeam, scorerEntity);
                gameEntity = this.gameService.createGame(gameEntity);
            } else {
                throw new DoubleInputException("Host team and guest team must not be the same");
            }
            GameUmpireEntity gameUmpire = this.mappingService.mapToGameUmpireEntity(gameEntity, umpires.get(0));
            gameUmpireEntities.add(gameUmpire);
            this.gameUmpireService.save(gameUmpire);
        } else if (umpires.isEmpty()) {
            throw new BadRequestError("No umpires provided");
        } else {
            throw new BadRequestError("Too many umpires provided");
        }

        // Create game state
        GameStateEntity gameState = new GameStateEntity();
        gameState.setGame(gameEntity);
        gameStateService.create(gameState);

        GameGetDto addedGame = this.mappingService.mapToGetGameDto(gameEntity, gameUmpireEntities);
        return new ResponseEntity<>(addedGame, HttpStatus.CREATED);
    }

    // Endpoint for updating an existing game after it is finished
    @Operation(summary = "updates an existing game after it is finished", description = "game must exist")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "game found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameFinishedGetDto.class))}), @ApiResponse(responseCode = "204", description = "no content"), @ApiResponse(responseCode = "400", description = "invalid JSON posted", content = @Content), @ApiResponse(responseCode = "401", description = "not authorized", content = @Content), @ApiResponse(responseCode = "404", description = "game not found"), @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @PutMapping("/{gameNr}")
    @RolesAllowed("user")
    public ResponseEntity<GameFinishedGetDto> finishGame(@PathVariable final Integer gameNr, @Valid @RequestBody final GamePutDto gamePutDto) {

        GameEntity updatedGameEntity = this.mappingService.mapUpdateGameDtoToGameEntity(gamePutDto);
        GameEntity foundGameEntity = this.gameService.findGameByGameNr(gameNr);
        updatedGameEntity.setId(foundGameEntity.getId());
        updatedGameEntity = this.gameService.finish(updatedGameEntity);

        List<GameUmpireEntity> gameUmpireEntities = gameUmpireService.findAllByGameId(foundGameEntity.getId());

        GameFinishedGetDto finishedGameDto = this.mappingService.mapToGetFinishedGameDto(updatedGameEntity, gameUmpireEntities);
        return new ResponseEntity<>(finishedGameDto, HttpStatus.CREATED);
    }

    @Operation(summary = "get a game by id", description = "game must exist")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "game found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameFinishedGetDto.class))}), @ApiResponse(responseCode = "204", description = "no content"), @ApiResponse(responseCode = "400", description = "invalid JSON posted", content = @Content), @ApiResponse(responseCode = "401", description = "not authorized", content = @Content), @ApiResponse(responseCode = "404", description = "game not found"), @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<GameGetDto> getGameById(@PathVariable final Long id) {
        GameEntity foundGameEntity = this.gameService.findGameById(id);
        List<GameUmpireEntity> gameUmpireEntities = gameUmpireService.findAllByGameId(foundGameEntity.getId());
        GameGetDto gameDto = this.mappingService.mapToGetGameDto(foundGameEntity, gameUmpireEntities);
        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }
}