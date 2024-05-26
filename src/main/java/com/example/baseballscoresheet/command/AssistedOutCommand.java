package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.dtos.responsible.ResponsibleDto;
import com.example.baseballscoresheet.model.entities.ResponsibleEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.TurnService;

import java.util.List;

public class AssistedOutCommand extends Command {
    private final int base;
    private final List<ResponsibleDto> responsible;

    public AssistedOutCommand(TurnService turnService, TurnEntity turn, int base, List<ResponsibleDto> responsible) {
        super(turnService, turn);
        this.base = base;
        this.responsible = responsible;
    }

    @Override
    public void execute() {

    }
}
