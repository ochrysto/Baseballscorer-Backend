package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.exceptionHandling.BadRequestError;
import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.dtos.action.OffensiveActionsDto;
import com.example.baseballscoresheet.model.dtos.diamond.DiamondDto;
import com.example.baseballscoresheet.model.dtos.gamestate.GameStateDto;
import com.example.baseballscoresheet.model.dtos.player.LineupPlayerGetDto;
import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.services.InningService;
import com.example.baseballscoresheet.services.LineupTeamPlayerService;
import com.example.baseballscoresheet.services.TurnService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Rest controller for handling requests related to the state of the game.
 */
@RestController
public class StateController {

    private final TurnService turnService;
    private final MappingService mappingService;
    private final InningService inningService;
    private final LineupTeamPlayerService playerService;

    /**
     * Constructor to initialize services.
     */
    public StateController(TurnService turnService, MappingService mappingService, InningService inningService, LineupTeamPlayerService playerService) {
        this.turnService = turnService;
        this.mappingService = mappingService;
        this.inningService = inningService;
        this.playerService = playerService;
    }

    /**
     * Retrieves the current state of the game.
     *
     * @param gid the game ID
     * @return the current game state
     */
    @GetMapping("/game/{gid}/state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "game state found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameStateDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "game not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "server error", content = @Content)
    })
    @RolesAllowed("user")
    public ResponseEntity<GameStateDto> getGameState(@PathVariable long gid) {
        GameEntity game = turnService.getGame(gid);
        TurnEntity turn = turnService.getLastTurn(game);

        LineupTeamPlayerEntity batter = turnService.getBatter(turn).getLineupTeamPlayer();
        LineupPlayerGetDto batterDto = mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(batter);

        // Get active runners on bases
        List<TurnEntity> activeRunners = turnService.getActiveRunners(game);
        Optional<TurnEntity> firstRunner = activeRunners.stream().filter(turnEntity -> turnEntity.getBase() == TurnEntity.Base.FIRST_BASE.getValue()).findFirst();
        Optional<TurnEntity> secondRunner = activeRunners.stream().filter(turnEntity -> turnEntity.getBase() == TurnEntity.Base.SECOND_BASE.getValue()).findFirst();
        Optional<TurnEntity> thirdRunner = activeRunners.stream().filter(turnEntity -> turnEntity.getBase() == TurnEntity.Base.THIRD_BASE.getValue()).findFirst();

        // Get scores by innings
        List<Integer> inningsAwayScores = inningService.getByGameAndTeam(game.getId(), InningEntity.Team.AWAY).stream().map(InningEntity::getScore).toList();
        List<Integer> inningsHomeScores = inningService.getByGameAndTeam(game.getId(), InningEntity.Team.HOME).stream().map(InningEntity::getScore).toList();

        GameStateDto gameState = new GameStateDto(
                game.getId(),
                game.getGameState().getAwayRuns(),
                game.getGameState().getHomeRuns(),
                game.getGameState().getAwayErrors(),
                game.getGameState().getHomeErrors(),
                game.getGameState().getAwayHits(),
                game.getGameState().getHomeHits(),
                game.getGameState().getAwayLOB(),
                game.getGameState().getHomeLOB(),
                inningsAwayScores,
                inningsHomeScores,
                turn.getInning().getInning(),
                turn.getInning().getBattingTeam(),
                turn.getInning().getOuts(),
                turn.getBalls(),
                turn.getStrikes(),
                null, // onDeck
                batterDto,
                firstRunner.map(turnEntity -> mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(turnEntity.getLineupTeamPlayer())).orElse(null),
                secondRunner.map(entity -> mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(entity.getLineupTeamPlayer())).orElse(null),
                thirdRunner.map(value -> mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(value.getLineupTeamPlayer())).orElse(null)
        );

        return ResponseEntity.ok(gameState);
    }

    /**
     * Retrieves the state of the defensive team.
     *
     * @param gid the game ID
     * @return the list of defensive team players
     */
    @GetMapping("/game/{gid}/defencive-team-state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "game state found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameStateDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "game not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "server error", content = @Content)
    })
    @RolesAllowed("user")
    public ResponseEntity<List<LineupPlayerGetDto>> getGameStateOfDefensiveTeam(@PathVariable long gid) {
        GameEntity game = turnService.getGame(gid);
        TurnEntity turn = turnService.getLastTurn(game);
        List<LineupTeamPlayerEntity> entities;
        List<LineupPlayerGetDto> dtos;

        switch (turn.getInning().getBattingTeam()) {
            case AWAY -> entities = playerService.findByGameAndTeam(game.getId(), game.getHost().getId());
            case HOME -> entities = playerService.findByGameAndTeam(game.getId(), game.getGuest().getId());
            default -> throw new BadRequestError("Cannot get current batting team. Check `StateController`.");
        }

        dtos = entities.stream().map(mappingService::mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto).toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Retrieves the diamonds for a specific team in a game.
     *
     * @param gid  the game ID
     * @param team the team (AWAY or HOME)
     * @return the list of diamonds
     */
    @GetMapping("/game/{gid}/team/{team}/diamonds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "diamonds found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OffensiveActionsDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "game not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "server error", content = @Content)
    })
    @RolesAllowed("user")
    public ResponseEntity<List<List<OffensiveActionsDto>>> getGameDiamonds(@PathVariable long gid, @PathVariable InningEntity.Team team) {
        GameEntity game = turnService.getGame(gid);

        List<InningEntity> innings = inningService.getByGameAndTeam(game.getId(), team);
        List<List<OffensiveActionsDto>> dtos = new ArrayList<>();

        for (InningEntity inning : innings) {
            List<OffensiveActionsDto> subDtos = generateDiamondsForInning(inning.getId());
            dtos.add(subDtos);
        }

        return ResponseEntity.ok(dtos);
    }

    /**
     * Generates diamonds for a specific inning.
     *
     * @param inningId the inning ID
     * @return the list of offensive actions
     */
    private List<OffensiveActionsDto> generateDiamondsForInning(long inningId) {
        List<TurnEntity> turns = turnService.getTurnsByInning(inningId);
        List<OffensiveActionsDto> offensiveActionsDtos = new ArrayList<>();

        int outCounter = 1;  // FIXME: wrong logic. e.g. runner in position 1 can be out second
        for (TurnEntity turn : turns) {
            if (outCounter > 3) {
                break;  // FIXME: panic
            }

            DiamondDto diamondDto = new DiamondDto();
            OffensiveActionsDto actionsDto = new OffensiveActionsDto();

            int base = turn.getBase();

            switch (turn.getCurrentStatus()) {
                case AT_BAT -> actionsDto.setAtBat(true);
                case IS_OUT -> {
                    diamondDto.setCenter("I".repeat(outCounter));
                    // FIXME: does not work properly
                    base -= 1;  // runner that is out hadn't reached destination base
                    outCounter++;
                }
            }

            diamondDto.setBase(base);

            int pos = 0;
            List<ActionEntity> actions = turnService.getActionsByTurn(turn.getId());
            for (ActionEntity action : actions) {
                if (action.getDistance() == 0) {
                    continue;
                }
                pos += action.getDistance();
                String symbol = getActionSymbol(action);
                switch (pos) {
                    case 1 -> diamondDto.setFirst(symbol);
                    case 2 -> diamondDto.setSecond(symbol);
                    case 3 -> diamondDto.setThird(symbol);
                    case 4 -> diamondDto.setHome(symbol);
                }
            }

            actionsDto.setFirstName(turn.getLineupTeamPlayer().getTeamPlayer().getPlayer().getFirstName());
            actionsDto.setLastName(turn.getLineupTeamPlayer().getTeamPlayer().getPlayer().getLastName());
            actionsDto.setJerseyNr(turn.getLineupTeamPlayer().getJerseyNr());
            actionsDto.setDiamond(diamondDto);
            offensiveActionsDtos.add(actionsDto);
        }
        return offensiveActionsDtos;
    }

    /**
     * Converts an action to its corresponding symbol.
     *
     * @param action the action entity
     * @return the action symbol
     */
    private String getActionSymbol(ActionEntity action) {
        return switch (action.getType()) {
            case HIT_SINGLE -> "1B";
            case HIT_DOUBLE -> "2B";
            case HIT_TRIPLE -> "3B";
            case HOME_RUN -> "HR";
            case STRIKEOUT -> "K";
            case ASSISTED_OUT -> action.getSequence().stream()
                    .map(entity -> String.valueOf(entity.getPosition()))
                    .reduce("", (res, next) -> res.isEmpty() ? next : res + "-" + next);
            default -> "??";
        };
    }
}
