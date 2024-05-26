package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.TurnService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Command {
    protected TurnService turnService;
    protected TurnEntity turn;

    public Command(TurnService turnService, TurnEntity turn) {
        this.turnService = turnService;
        this.turn = turn;
    }

    abstract void execute();

    public void undo() {}
}
