package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.TurnService;

public class HoldCommand extends Command {
    private final int base;

    public HoldCommand(TurnService turnService, TurnEntity turn, int base) {
        super(turnService, turn);
        this.base = base;
    }

    @Override
    public void execute() {

    }
}
