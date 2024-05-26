package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.TurnService;

public class BallCommand extends Command {
    public BallCommand(TurnService turnService, TurnEntity turn) {
        super(turnService, turn);
    }

    @Override
    public void execute() {
        if (turn.getBalls() >= TurnService.MAX_BALLS) {
//            throw new Exception("The maximum number of balls has already been reached.");  # FIXME
        }
    }
}
