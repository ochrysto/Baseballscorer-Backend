package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.GameStateService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HitDoubleCommand extends Command {
    private final GameStateService gameStateService;

    public HitDoubleCommand(GameStateService gameStateService) {
        super();
        this.gameStateService = gameStateService;
    }

    @Override
    public void execute() {
        List<TurnEntity> runners = turnService.getActiveRunners(turn.getInning().getGame());
        if (runners.isEmpty()) {
            turnService.moveBatterToBase(turn, 2);
            ActionEntity action = new ActionEntity();
            action.setTurn(turn);
            action.setType(ActionEntity.Type.HIT_DOUBLE);
            action.setDistance(2);
            action.setStandalone(true);
            turnService.createNewAction(action);
            turnService.createNewTurn(turn);
        } else {
            ActionEntity action = new ActionEntity();
            action.setTurn(turn);
            action.setType(ActionEntity.Type.HIT_DOUBLE);
            action.setDistance(2);
            action.setStandalone(false);
            turnService.createNewAction(action);
        }

        gameStateService.increaseHitPoints(turn.getInning().getGame(), turn.getInning().getBattingTeam());
    }
}
