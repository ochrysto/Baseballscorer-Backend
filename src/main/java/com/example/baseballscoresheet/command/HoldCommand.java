package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Component
public class HoldCommand extends Command {
    private int base;

    @Override
    public void execute() {
        TurnEntity runnerTurn = turnService.getRunnerByBase(turn, base);
        ActionEntity lastAction = turnService.getLastAction(turn);

        ActionEntity newAction = new ActionEntity();
        newAction.setTurn(runnerTurn);
        newAction.setType(ActionEntity.Type.HOLD);
        newAction.setDistance(0);

        if (lastAction != null && !lastAction.isStandalone()) {
            newAction.setLinkedAction(lastAction);
            newAction.setStandalone(false);
            turnService.createNewAction(newAction);
            turnService.updateTurnsAndActions(lastAction, turn);
        } else {
            newAction.setStandalone(true);
            turnService.createNewAction(newAction);
        }
    }
}
