package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HitSingleCommand extends Command {
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
    }
}
