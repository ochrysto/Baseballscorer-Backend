package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.exceptionHandling.BadRequestError;
import com.example.baseballscoresheet.exceptionHandling.ResourceNotFoundException;
import com.example.baseballscoresheet.model.dtos.responsible.ResponsibleDto;
import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.ResponsibleEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.services.ResponsibleService;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Component
public class AssistedOutCommand extends Command {
    private int base;
    private List<ResponsibleDto> responsible;
    private final ResponsibleService responsibleService;

    public AssistedOutCommand(ResponsibleService responsibleService) {
        this.responsibleService = responsibleService;
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
        ActionEntity assistedOutAction = new ActionEntity();
        assistedOutAction.setTurn(runnerTurn);
        assistedOutAction.setType(ActionEntity.Type.ASSISTED_OUT);
        assistedOutAction.setDistance(0);
        assistedOutAction.setLinkedAction(lastAction != null && !lastAction.isStandalone() ? lastAction : null);
        assistedOutAction.setStandalone(lastAction == null || lastAction.isStandalone());
        turnService.createNewAction(assistedOutAction);

        for (int count = 0; count < responsible.size(); count++) {
            ResponsibleEntity responsibleEntity = new ResponsibleEntity();
            responsibleEntity.setAction(assistedOutAction);
            responsibleEntity.setCounter(count + 1);
            responsibleEntity.setDefencePosition(responsible.get(count).getDefencePosition());
            responsibleService.save(responsibleEntity);
        }

        runnerTurn.setCurrentStatus(TurnEntity.Status.IS_OUT);
        turnService.updateTurn(runnerTurn);

        turnService.updateTurnsAndActions(assistedOutAction, turn);
    }
}
