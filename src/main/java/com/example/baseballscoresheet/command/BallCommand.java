package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.TurnService;

public class BallCommand implements Command {
    private final TurnService service;
    private final TurnEntity turn;

    public BallCommand(TurnService service, TurnEntity turn) {
        this.service = service;
        this.turn = turn;
    }

    @Override
    public void execute() {
        if (turn.getBalls() >= TurnService.MAX_BALLS) {
//            throw new Exception("The maximum number of balls has already been reached.");  # FIXME
        }
    }
}
