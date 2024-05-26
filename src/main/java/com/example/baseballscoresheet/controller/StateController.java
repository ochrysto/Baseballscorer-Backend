package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.mapping.PlayerMapper;
import com.example.baseballscoresheet.model.dtos.gamestate.GameStateDto;
import com.example.baseballscoresheet.model.dtos.player.GetPlayerDto;
import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.PlayerEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.TurnService;
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

    public StateController(TurnService turnService) {
        this.turnService = turnService;
    }

    @GetMapping("/game/{gid}/state")
    public ResponseEntity<GameStateDto> getGameState(@PathVariable long gid) {
        GameEntity game = turnService.getGame(gid);
        TurnEntity turn = turnService.getLastTurn(game);

        PlayerEntity batter = turnService.getBatter(turn).getPlayer();
        GetPlayerDto batterDto = PlayerMapper.INSTANCE.playerEntityToGetPlayerDto(batter);

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
                new ArrayList<>(), // TODO: Implement
                new ArrayList<>(), // TODO: Implement
                turn.getInning().getInning(),
                turn.getInning().getBattingTeam(),
                turn.getInning().getOuts(),
                turn.getBalls(),
                turn.getStrikes(),
                null, // onDeck
                batterDto, // batter
                first_runner.isPresent() ? PlayerMapper.INSTANCE.playerEntityToGetPlayerDto(first_runner.get().getPlayer()) : null, // firstBase
                second_runner.isPresent() ? PlayerMapper.INSTANCE.playerEntityToGetPlayerDto(second_runner.get().getPlayer()) : null, // secondBase
                third_runner.isPresent() ? PlayerMapper.INSTANCE.playerEntityToGetPlayerDto(third_runner.get().getPlayer()) : null  // thirdBase
        );

        return ResponseEntity.ok(gameState);
    }
}
