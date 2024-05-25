package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.model.dtos.gamestate.GameStateDto;
import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.TurnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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

        GameStateDto gameState = new GameStateDto(
                game.getId(),
                game.getAwayRuns(),
                game.getHomeRuns(),
                game.getAwayErrors(),
                game.getHomeErrors(),
                game.getAwayHits(),
                game.getHomeHits(),
                game.getAwayLOB(),
                game.getHomeLOB(),
                new ArrayList<>(), // TODO: Implement
                new ArrayList<>(), // TODO: Implement
                turn.getInning().getInning(),
                turn.getInning().getBattingTeam(),
                turn.getInning().getOuts(),
                turn.getBalls(),
                turn.getStrikes(),
                null, // onDeck
                null, // batter
                null, // firstBase
                null, // secondBase
                null  // thirdBase
        );

        gameState = turnService.populateActiveRunners(turn, gameState);

        return ResponseEntity.ok(gameState);
    }
}
