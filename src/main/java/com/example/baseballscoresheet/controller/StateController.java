package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.dtos.action.OffensiveActionsDto;
import com.example.baseballscoresheet.model.dtos.diamond.DiamondDto;
import com.example.baseballscoresheet.model.dtos.gamestate.GameStateDto;
import com.example.baseballscoresheet.model.dtos.player.GetPlayerFromLineupDto;
import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.services.InningService;
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

@RestController
public class StateController {

    private final TurnService turnService;
    private final MappingService mappingService;
    private final InningService inningService;

    public StateController(TurnService turnService, MappingService mappingService, InningService inningService1) {
        this.turnService = turnService;
        this.mappingService = mappingService;
        this.inningService = inningService1;
    }

    @GetMapping("/game/{gid}/state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "game state found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameStateDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "game not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
    })
    @RolesAllowed("user")
    public ResponseEntity<GameStateDto> getGameState(@PathVariable long gid) {
        GameEntity game = turnService.getGame(gid);
        TurnEntity turn = turnService.getLastTurn(game);

        LineupTeamPlayerEntity batter = turnService.getBatter(turn).getLineupTeamPlayer();
        GetPlayerFromLineupDto batterDto = mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(batter);

        List<TurnEntity> activeRunners = turnService.getActiveRunners(game);
        Optional<TurnEntity> first_runner = activeRunners.stream().filter(turnEntity -> turnEntity.getBase() == TurnEntity.Base.FIRST_BASE.getValue()).findFirst();
        Optional<TurnEntity> second_runner = activeRunners.stream().filter(turnEntity -> turnEntity.getBase() == TurnEntity.Base.SECOND_BASE.getValue()).findFirst();
        Optional<TurnEntity> third_runner = activeRunners.stream().filter(turnEntity -> turnEntity.getBase() == TurnEntity.Base.THIRD_BASE.getValue()).findFirst();

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
                //new ArrayList<>(), // TODO: Implement
                //new ArrayList<>(), // TODO: Implement
                null,
                turn.getInning().getInning(),
                turn.getInning().getBattingTeam(),
                turn.getInning().getOuts(),
                turn.getBalls(),
                turn.getStrikes(),
                null, // onDeck
                batterDto, // batter
                first_runner.map(turnEntity -> mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(turnEntity.getLineupTeamPlayer())).orElse(null), // firstBase
                second_runner.map(entity -> mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(entity.getLineupTeamPlayer())).orElse(null), // secondBase
                third_runner.map(value -> mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(value.getLineupTeamPlayer())).orElse(null)  // thirdBase
        );

        return ResponseEntity.ok(gameState);
    }

    @GetMapping("/game/{gid}/team/{team}/diamonds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "diamonds found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OffensiveActionsDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "game not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = @Content),
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

//        for (LineupTeamPlayerEntity player : lineupTeamPlayers) {
//            OffensiveActionsDto dto = new OffensiveActionsDto();
//            dto.setFirstName(player.getTeamPlayer().getPlayer().getFirstName());
//            dto.setLastName(player.getTeamPlayer().getPlayer().getLastName());
//            dto.setJerseyNumber(player.getJerseyNr());
//        }
//
//        return ResponseEntity.ok(offensiveActionsDtoList);
    }

    private List<OffensiveActionsDto> generateDiamondsForInning(long inningId) {
//        List<LineupTeamPlayerEntity> lineupTeamPlayers = new ArrayList<>();
        List<TurnEntity> turns = this.turnService.getTurnsByInning(inningId);
//        List<DiamondDto> diamondDtos = new ArrayList<>();
        List<OffensiveActionsDto> offensiveActionsDtos = new ArrayList<>();

        int out_counter = 1;
        for (TurnEntity turn : turns) {
            if (out_counter > 3) {
                break;  // FIXME
            }

            DiamondDto diamondDto = new DiamondDto();
            OffensiveActionsDto actionsDto = new OffensiveActionsDto();
            diamondDto.setBase(turn.getBase());
            switch (turn.getCurrentStatus()) {
                case AT_BAT -> {
                    actionsDto.setAtBat(true);
                }
                case IS_OUT -> {
                    diamondDto.setCenter("I".repeat(out_counter));
                    out_counter++;
                }
            }

            int pos = turn.getBase();
            List<ActionEntity> actions = turnService.getActionsByTurn(turn.getId());
            for (ActionEntity a : actions) {
                if (a.getDistance() == 0) {
                    continue;
                }
                String symbol = getActionSymbol(a);
                switch (a.getDistance()) {
                    case 1 -> diamondDto.setFirst(symbol);
                    case 2 -> diamondDto.setSecond(symbol);
                    case 3 -> diamondDto.setThird(symbol);
                    case 4 -> diamondDto.setHome(symbol);
                }
                pos -= a.getDistance();
            }
//            diamondDtos.add(diamondDto);
            actionsDto.setFirstName(turn.getLineupTeamPlayer().getTeamPlayer().getPlayer().getFirstName());
            actionsDto.setLastName(turn.getLineupTeamPlayer().getTeamPlayer().getPlayer().getLastName());
            actionsDto.setJerseyNr(turn.getLineupTeamPlayer().getJerseyNr());
            actionsDto.setDiamond(diamondDto);
            offensiveActionsDtos.add(actionsDto);
        }
        return offensiveActionsDtos;

        // BAD ORDER! We do not know which player was first at bat and first out
//        for (LineupTeamPlayerEntity player : lineupTeamPlayers) {
//            Optional<TurnEntity> turn = turns.stream().filter(t -> t.getLineupTeamPlayer().getId().equals(player.getId())).findFirst();
//            if (turn.isPresent()) {
//
//            } else {
//
//            }
//        }
    }

    private LineupTeamPlayerEntity generateDiamonds(LineupTeamPlayerEntity player) {
        List<TurnEntity> turns = this.turnService.getTurnsByPlayerId(player.getId());

        for (TurnEntity turn : turns) {
            DiamondDto diamondDto = new DiamondDto();
            diamondDto.setBase(turn.getBase());

            int final_base = turn.getBase();
            for (ActionEntity action : turn.getActions()) {
                if (action.getDistance() == 0) {
                    continue;
                }
                String symbol = getActionSymbol(action);
                switch (final_base) {
                    case 4 -> diamondDto.setHome(symbol);
                    case 3 -> diamondDto.setThird(symbol);
                    case 2 -> diamondDto.setSecond(symbol);
                    case 1 -> diamondDto.setFirst(symbol);
                }
            }
        }

        return null;
    }

    private String getActionSymbol(ActionEntity action) {
        return switch (action.getType()) {
            case HIT_SINGLE -> "1B";
            case HIT_DOUBLE -> "2B";
            case HIT_TRIPLE -> "3B";
            case HOME_RUN -> "HR";
            case STRIKEOUT -> "K";
            default -> "??";
        };
    }
}
