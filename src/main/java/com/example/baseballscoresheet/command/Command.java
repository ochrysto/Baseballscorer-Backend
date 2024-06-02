package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.TurnService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Command {
    @Autowired
    protected TurnService turnService;
    @Setter
    protected TurnEntity turn;

    abstract void execute();

    public void undo() {}
}
