package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.TurnService;
import com.example.baseballscoresheet.exceptionHandling.BadRequestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BallCommand extends Command {
    @Override
    public void execute() {
        if (turn.getBalls() >= TurnService.MAX_BALLS) {
            throw new BadRequestError("The maximum number of balls has already been reached.");
        }
        turn = turnService.increaseBallsCount(turn);
        ActionEntity action = new ActionEntity();
        action.setTurn(turn);
        action.setType(ActionEntity.Type.BALL);
        action = turnService.createNewAction(action);

        if (turn.getBalls() == 4) {
            turnService.advanceRunners(turn.getInning().getGame(), action);
            turnService.createNewTurn(turn);
        }
    }
}
