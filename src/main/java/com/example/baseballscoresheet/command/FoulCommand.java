package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.TurnService;

public class FoulCommand extends Command {
    public FoulCommand(TurnService turnService, TurnEntity turn) {
        super(turnService, turn);
    }

    @Override
    public void execute() {

    }
}
