package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.GameStateService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HitSingleCommand extends Command {
    private final GameStateService gameStateService;

    public HitSingleCommand(GameStateService gameStateService) {
        super();
        this.gameStateService = gameStateService;
    }

    @Override
    public void execute() {
        List<TurnEntity> runners = turnService.getActiveRunners(turn.getInning().getGame());
        if (!runners.isEmpty()) {
            ActionEntity action = new ActionEntity();
            action.setTurn(turn);
            action.setType(ActionEntity.Type.HIT_SINGLE);
            action.setDistance(1);
            action.setStandalone(false);
            turnService.createNewAction(action);
        } else {
            turnService.moveBatterToBase(turn, 1);
            ActionEntity action = new ActionEntity();
            action.setTurn(turn);
            action.setType(ActionEntity.Type.HIT_SINGLE);
            action.setDistance(1);
            action.setStandalone(true);
            turnService.createNewAction(action);
            turnService.createNewTurn(turn);
        }

        gameStateService.increaseHitPoints(turn.getInning().getGame(), turn.getInning().getBattingTeam());
    }
}
