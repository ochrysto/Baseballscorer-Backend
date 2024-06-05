package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HitTripleCommand extends Command {
    @Override
    public void execute() {
        List<TurnEntity> runners = turnService.getActiveRunners(turn.getInning().getGame());
        if (runners.isEmpty()) {
            turnService.moveBatterToBase(turn, 3);
            ActionEntity action = new ActionEntity();
            action.setTurn(turn);
            action.setType(ActionEntity.Type.HIT_TRIPLE);
            action.setDistance(3);
            action.setStandalone(true);
            turnService.createNewAction(action);
            turnService.createNewTurn(turn);
        } else {
            ActionEntity action = new ActionEntity();
            action.setTurn(turn);
            action.setType(ActionEntity.Type.HIT_TRIPLE);
            action.setDistance(3);
            action.setStandalone(false);
            turnService.createNewAction(action);
        }
    }
}
