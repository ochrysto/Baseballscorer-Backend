package com.example.baseballscoresheet;

import com.example.baseballscoresheet.model.dtos.action.ActionDto;
import com.example.baseballscoresheet.model.dtos.button.ButtonDto;
import com.example.baseballscoresheet.model.entities.ActionEntity;

import java.util.stream.Collectors;

public class ActionUtils {

    // TODO: if we change Enum names from screaming snake case to camel case - use this method
    public static String camelToSentence(String s) {
        return s.replaceAll("([A-Z])", " $1").toLowerCase().trim();
    }

    public static String screamingSnakeToSentence(String s) {
        // Replace underscores with spaces and convert to lowercase
        String sentence = s.replaceAll("_", " ").toLowerCase();

        // Capitalize the first letter of the resulting string
        if (!sentence.isEmpty()) {
            sentence = sentence.substring(0, 1).toUpperCase() + sentence.substring(1);
        }

        return sentence;
    }

    public static ButtonDto createButton(ActionEntity.Type actionType) {
        ButtonDto buttonDTO = new ButtonDto();
        buttonDTO.setButton(screamingSnakeToSentence(actionType.name()));
        buttonDTO.setActionType(actionType);
        buttonDTO.setResponsibleRequired(ActionEntity.isResponsibleRequired(actionType));
        buttonDTO.setMultipleResponsibleRequired(ActionEntity.areMultipleResponsibleRequired(actionType));
        return buttonDTO;
    }

    public static ActionDto getActionsForBatter() {
        ActionDto actionDTO = new ActionDto();
        actionDTO.setOut(ActionEntity.outBatter().stream().map(ActionUtils::createButton).collect(Collectors.toList()));
        actionDTO.setSafe(ActionEntity.safeBatter().stream().map(ActionUtils::createButton).collect(Collectors.toList()));
        actionDTO.setError(ActionEntity.errorBatter().stream().map(ActionUtils::createButton).collect(Collectors.toList()));
        return actionDTO;
    }

    public static ActionDto getActionsForHoldRunner() {
        ActionDto actionDTO = new ActionDto();
        actionDTO.setOut(ActionEntity.outRunnerHold().stream().map(ActionUtils::createButton).collect(Collectors.toList()));
        actionDTO.setSafe(ActionEntity.safeRunnerHold().stream().map(ActionUtils::createButton).collect(Collectors.toList()));
        actionDTO.setError(ActionEntity.errorRunnerHold().stream().map(ActionUtils::createButton).collect(Collectors.toList()));
        return actionDTO;
    }

    public static ActionDto getActionsForAdvanceRunner() {
        ActionDto actionDTO = new ActionDto();
        actionDTO.setOut(ActionEntity.outRunnerAdvance().stream().map(ActionUtils::createButton).collect(Collectors.toList()));
        actionDTO.setSafe(ActionEntity.safeRunnerAdvance().stream().map(ActionUtils::createButton).collect(Collectors.toList()));
        actionDTO.setError(ActionEntity.errorRunnerAdvance().stream().map(ActionUtils::createButton).collect(Collectors.toList()));
        return actionDTO;
    }
}