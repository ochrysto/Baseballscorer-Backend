package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.exceptionHandling.BadRequestError;
import com.example.baseballscoresheet.exceptionHandling.ResourceNotFoundException;
import com.example.baseballscoresheet.model.dtos.responsible.ResponsibleDto;
import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.ResponsibleEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.InningService;
import com.example.baseballscoresheet.services.ResponsibleService;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Component
public class AssistedOutCommand extends Command {
    private final InningService inningService;
    private int base;
    private int distance;
    private List<ResponsibleDto> responsible;
    private final ResponsibleService responsibleService;

    public AssistedOutCommand(ResponsibleService responsibleService, InningService inningService) {
        this.responsibleService = responsibleService;
        this.inningService = inningService;
    }

    @Override
    public void execute() {
        if (responsible.size() < 2) {
            throw new BadRequestError("There must be more than two defensive players involved.");
        }

        TurnEntity runnerTurn = turnService.getRunnerByBase(turn, base);
        if (runnerTurn == null) {
            throw new ResourceNotFoundException("No runner found on base " + base);
        }

        ActionEntity lastAction = turnService.getLastAction(turn);

        // create action
        // TODO: what if the initial linked action is not a batter?
        ActionEntity assistedOutAction = new ActionEntity();
        assistedOutAction.setTurn(runnerTurn);
        assistedOutAction.setType(ActionEntity.Type.ASSISTED_OUT);
        assistedOutAction.setDistance(distance);  // FIXME: quick and dirty - e.g. runner at 3rd base that ran to the home base has distance 1 he had no reach home plate
        boolean mustHaveLinkedAction = lastAction != null && !lastAction.isStandalone() && !lastAction.isProceed();
        boolean isStandalone = !mustHaveLinkedAction;
        assistedOutAction.setLinkedAction(mustHaveLinkedAction ? lastAction : null);
        assistedOutAction.setStandalone(isStandalone);
        assistedOutAction = turnService.createNewAction(assistedOutAction);

        for (int count = 0; count < responsible.size(); count++) {
            ResponsibleEntity responsibleEntity = new ResponsibleEntity();
            responsibleEntity.setAction(assistedOutAction);
            responsibleEntity.setCounter(count + 1);
            responsibleEntity.setDefencePosition(responsible.get(count).getDefencePosition());
            responsibleService.save(responsibleEntity);
        }

        runnerTurn.setCurrentStatus(TurnEntity.Status.IS_OUT);
        turnService.updateTurn(runnerTurn);

        inningService.increaseOuts(turn.getInning());

        if (!isStandalone)
            turnService.updateTurnsAndActions(assistedOutAction, turn, base);

        if (!turnService.isBatterAtBat(turn))
            turnService.createNewTurn(turn);

        if (this.turnService.isEndOfInning(turn.getInning()))
            turnService.createNewTurn(turn);

    }
}
