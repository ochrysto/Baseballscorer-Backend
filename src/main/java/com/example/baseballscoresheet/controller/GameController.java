package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.Utility;
import com.example.baseballscoresheet.exceptionHandling.DoubleInputException;
import com.example.baseballscoresheet.exceptionHandling.PlayerIsNotAvailableException;
import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.dtos.game.*;
import com.example.baseballscoresheet.model.dtos.lineup.AddLineupDto;
import com.example.baseballscoresheet.model.dtos.lineup.GetLineupDto;
import com.example.baseballscoresheet.model.dtos.player.PlayerForLineupDto;
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
    private final LineupService lineupService;
    private final TeamPlayerService teamPlayerService;
    private final LineupTeamPlayerService lineupTeamPlayerService;
    private final PositionService positionService;

    public GameController(GameService gameService, MappingService mappingService, AssociationService associationService, LeagueService leagueService, UmpireService umpireService, ScorerService scorerService, TeamService teamService, GameUmpireService gameUmpireService, LineupService lineupService, TeamPlayerService teamPlayerService, LineupTeamPlayerService lineupTeamPlayerService, PositionService positionService) {
        this.gameService = gameService;
        this.mappingService = mappingService;
        this.associationService = associationService;
        this.leagueService = leagueService;
        this.umpireService = umpireService;
        this.scorerService = scorerService;
        this.teamService = teamService;
        this.gameUmpireService = gameUmpireService;
        this.lineupService = lineupService;
        this.teamPlayerService = teamPlayerService;
        this.lineupTeamPlayerService = lineupTeamPlayerService;
        this.positionService = positionService;
    }

    // Endpoint for saving a new game
    @Operation(summary = "saves a new game")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "created game",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(
                            implementation = GetGameWithoutLineupsDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @PostMapping
    @RolesAllowed("user")
    public ResponseEntity<GetGameWithoutLineupsDto> createGame(@RequestBody @Valid AddGameDto addGameDto) {
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
            if (umpires.get(0).equals(umpires.get(1))) {
                throw new DoubleInputException("First und second umpire must not be the same");
            } else {
                // saving game
                if (!hostTeam.equals(guestTeam)) {
                    gameEntity = this.mappingService.mapToGameEntity(addGameDto, associationEntity, leagueEntity, scorerEntity);
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
        } else {
            // saving game
            if (!hostTeam.equals(guestTeam)) {
                gameEntity = this.mappingService.mapToGameEntity(addGameDto, associationEntity, leagueEntity, scorerEntity);
                gameEntity = this.gameService.createGame(gameEntity);
            } else {
                throw new DoubleInputException("Host team and guest team must not be the same");
            }
            GameUmpireEntity gameUmpire = this.mappingService.mapToGameUmpireEntity(gameEntity, umpires.get(0));
            gameUmpireEntities.add(gameUmpire);
            this.gameUmpireService.save(gameUmpire);
        }
        GetGameWithoutLineupsDto addedGame = this.mappingService.mapToGetGameDto(gameEntity, gameUmpireEntities);
        return new ResponseEntity<>(addedGame, HttpStatus.CREATED);
    }

    // Endpoint for updating an existing game after it is finished
    @Operation(summary = "updates an existing game after it is finished", description = "game must exist")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "game found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetGameWithLineupsDto.class))}), @ApiResponse(responseCode = "204", description = "no content"), @ApiResponse(responseCode = "400", description = "invalid JSON posted", content = @Content), @ApiResponse(responseCode = "401", description = "not authorized", content = @Content), @ApiResponse(responseCode = "404", description = "game not found"), @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @PutMapping("/{gameNr}")
    @RolesAllowed("user")
    public ResponseEntity<GetFinishedGameDto> finishGame(@PathVariable final Integer gameNr, @Valid @RequestBody final UpdateGameDto updateGameDto) {

        GameEntity updatedGameEntity = this.mappingService.mapUpdateGameDtoToGameEntity(updateGameDto);
        GameEntity foundGameEntity = this.gameService.findGameByGameNr(gameNr);
        updatedGameEntity.setId(foundGameEntity.getId());
        updatedGameEntity = this.gameService.finish(updatedGameEntity);

        List<GameUmpireEntity> gameUmpireEntities = gameUmpireService.findAllByGameId(foundGameEntity.getId());

        GetFinishedGameDto finishedGameDto = this.mappingService.mapToGetFinishedGameDto(updatedGameEntity, gameUmpireEntities);
        return new ResponseEntity<>(finishedGameDto, HttpStatus.CREATED);
    }

    // Endpoint for adding two filled lineups to a game
    @Operation(summary = "adds two filled lineups to a game", description = "game must exist")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "lineups added", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetGameWithLineupsDto.class))}), @ApiResponse(responseCode = "204", description = "no content"), @ApiResponse(responseCode = "400", description = "invalid JSON posted", content = @Content), @ApiResponse(responseCode = "401", description = "not authorized", content = @Content), @ApiResponse(responseCode = "404", description = "game not found"), @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @PutMapping("/{gameNr}/lineup")
    @RolesAllowed("user")
    public ResponseEntity<GetGameWithLineupsDto> addLineupsToGame(@PathVariable final Integer gameNr, @Valid @RequestBody final List<AddLineupDto> newLineups) {
        if (newLineups.get(0).isGuestTeam() == newLineups.get(1).isGuestTeam()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The lineups must belong to different teams");
        } else {
            List<LineupTeamPlayerEntity> addedLineupTeamPlayers = new ArrayList<>();
            // both lineups are mapped and saved
            for (AddLineupDto addLineupDto : newLineups) {
                TeamEntity teamEntity = this.teamService.findTeamById(addLineupDto.getTeamId());
                LineupEntity lineupEntity = this.mappingService.mapTeamEntityToLineupEntity(teamEntity);
                this.lineupService.saveLineup(lineupEntity);
            }

            // a new LineupTeamPlayerEntity object is created for each player that can be found in the two lineups
            for (AddLineupDto addLineupDto : newLineups) {
                for (PlayerForLineupDto playerForLineupDto : addLineupDto.getPlayerList()) {
                    if (!Utility.checkIfPlayerIsAlreadyAssignedToLineup(playerForLineupDto.getPlayerId())) {
                        LineupEntity lineupEntity2 = lineupService.findLineupByTeamId(addLineupDto.getTeamId());
                        TeamPlayerEntity teamPlayer = this.teamPlayerService.findTeamPlayerEntityByTeamIdAndPlayerId(lineupEntity2.getTeam().getId(), playerForLineupDto.getPlayerId());
                        PositionEntity position = this.positionService.findById(playerForLineupDto.getPositionId());
                        LineupTeamPlayerEntity lineupTeamPlayerEntity = this.mappingService.mapToLineupTeamPlayerEntity(playerForLineupDto, lineupEntity2, teamPlayer, position);
                        addedLineupTeamPlayers.add(lineupTeamPlayerEntity);
                        this.lineupTeamPlayerService.saveLineupTeamPlayerEntity(lineupTeamPlayerEntity);
                    } else {
                        throw new PlayerIsNotAvailableException("Player with id " + playerForLineupDto.getPlayerId() + " already assigned to lineup");
                    }
                }
            }

            GetLineupDto guestLineup = new GetLineupDto();
            GetLineupDto hostLineup = new GetLineupDto();
            guestLineup.setPlayerList(new ArrayList<>());
            hostLineup.setPlayerList(new ArrayList<>());

            for (LineupTeamPlayerEntity lineupTeamPlayer : addedLineupTeamPlayers) {
                if (lineupTeamPlayer.getTeamPlayer().getTeam().getId().equals(newLineups.get(0).getTeamId())) {
                    if (newLineups.get(0).isHostTeam()) {
                        hostLineup.setTeamId(lineupTeamPlayer.getTeamPlayer().getTeam().getId());
                        hostLineup.getPlayerList().add(this.mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(lineupTeamPlayer));
                    } else if (newLineups.get(0).isGuestTeam()) {
                        guestLineup.setTeamId(lineupTeamPlayer.getTeamPlayer().getTeam().getId());
                        guestLineup.getPlayerList().add(this.mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(lineupTeamPlayer));
                    }
                } else if (lineupTeamPlayer.getTeamPlayer().getTeam().getId().equals(newLineups.get(1).getTeamId())) {
                    if (newLineups.get(1).isHostTeam()) {
                        hostLineup.setTeamId(lineupTeamPlayer.getTeamPlayer().getTeam().getId());
                        hostLineup.getPlayerList().add(this.mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(lineupTeamPlayer));
                    } else if (newLineups.get(1).isGuestTeam()) {
                        guestLineup.setTeamId(lineupTeamPlayer.getTeamPlayer().getTeam().getId());
                        guestLineup.getPlayerList().add(this.mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(lineupTeamPlayer));
                    }
                }
            }
            GameEntity game = this.gameService.findGameByGameNr(gameNr);
            LineupEntity foundedHostLineupEntity = this.lineupService.findLineupByTeamId(hostLineup.getTeamId());
            LineupEntity foundedGuestLineupEntity = this.lineupService.findLineupByTeamId(guestLineup.getTeamId());
            game = this.gameService.saveLineupsToGame(game, foundedHostLineupEntity, foundedGuestLineupEntity);
            GetGameWithLineupsDto getGameWithLineupsDto = this.mappingService.mapToGetGameWithLineupsDto(game, foundedHostLineupEntity, foundedGuestLineupEntity);
            return new ResponseEntity<>(getGameWithLineupsDto, HttpStatus.CREATED);
        }
    }
}
