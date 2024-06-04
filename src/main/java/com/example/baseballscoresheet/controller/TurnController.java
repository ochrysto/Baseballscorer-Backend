package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.exceptionHandling.BadRequestError;
import com.example.baseballscoresheet.model.dtos.message.MessageDto;
import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.TurnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game/{gid}/turn")
public class TurnController {
    private final TurnService turnService;

    public TurnController(TurnService turnService) {
        this.turnService = turnService;
    }

    /**
     * Create an initial turn.
     *
     * @param gid the game ID
     * @return ResponseEntity containing a success message or error message
     */
    @PostMapping
    public ResponseEntity<MessageDto> createTurn(@PathVariable long gid) {
        // define local variables
        GameEntity game = turnService.getGame(gid);
        TurnEntity turn = turnService.getLastTurnOrElseNull(game);

        if (turn != null) {
            throw new BadRequestError("There is already a turn in the game");
        }

        this.turnService.createFirstTurn(game);

        return ResponseEntity.ok(new MessageDto("success"));
    }
}
