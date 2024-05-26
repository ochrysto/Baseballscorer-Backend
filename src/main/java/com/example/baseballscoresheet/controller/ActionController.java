package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.ActionUtils;
import com.example.baseballscoresheet.command.*;
import com.example.baseballscoresheet.mapping.ResponsibleMapper;
import com.example.baseballscoresheet.model.dtos.action.ActionGetDto;
import com.example.baseballscoresheet.model.dtos.action.ActionPostDto;
import com.example.baseballscoresheet.model.dtos.action.RunnerActionDto;
import com.example.baseballscoresheet.model.dtos.button.ButtonDto;
import com.example.baseballscoresheet.model.dtos.message.MessageDto;
import com.example.baseballscoresheet.model.dtos.responsible.ResponsibleDto;
import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.model.entities.ResponsibleEntity;
import com.example.baseballscoresheet.services.TurnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/game/{gid}/action")
public class ActionController {

    private final TurnService turnService;

    public ActionController(TurnService turnService) {
        this.turnService = turnService;
    }

    /**
     * Get allowed actions at the current step.
     *
     * @param gid the game ID
     * @return ResponseEntity containing the allowed actions
     */
    @GetMapping
    public ResponseEntity<ActionGetDto> getAllowedActions(@PathVariable long gid) {
        GameEntity game = turnService.getGame(gid);
        TurnEntity turn = turnService.getLastTurn(game);
        ActionGetDto availableActions = getAllowedActions(turn);
        return ResponseEntity.ok(availableActions);
    }

    /**
     * Register an action.
     *
     * @param gid the game ID
     * @param actionPostDto the action to be registered
     * @return ResponseEntity containing a success message or error message
     */
    @PostMapping
    public ResponseEntity<MessageDto> registerAction(@PathVariable long gid, @RequestBody ActionPostDto actionPostDto) {
        GameEntity game = null;
        try {
            game = turnService.getGame(gid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TurnEntity turn = null;
        try {
            turn = turnService.getLastTurn(game);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int base = actionPostDto.getBase();
        ActionEntity.Type type = actionPostDto.getType();
        // extract and map responsible(s) to list of entities
        List<ResponsibleDto> responsible = actionPostDto.getResponsible();
//        List<ResponsibleEntity> responsibleEntities = ResponsibleMapper.INSTANCE.responsibleDtoToResponsibleEntities(responsible);

        // Validate the action
        ActionGetDto allowedActions = getAllowedActions(turn);
        if (!isActionAllowed(base, type, allowedActions)) {
            return ResponseEntity.badRequest().body(new MessageDto("Invalid Action: The action is not allowed at this step."));
        }

        executeAction(type, base, responsible, turn);
        return ResponseEntity.ok(new MessageDto("success"));
    }

    /**
     * Return allowed actions for the current turn.
     *
     * @param turn the current turn
     * @return ActionGetDto containing the allowed actions
     */
    private ActionGetDto getAllowedActions(TurnEntity turn) {
        ActionGetDto availableActions = new ActionGetDto();

        ActionEntity lastAction;
        try {
            lastAction = turnService.getLastAction(turn);
        } catch (Exception e) {
            lastAction = null;
        }

        List<TurnEntity> offence = turnService.getActiveRunners(turn.getInning().getGame());
        boolean is_first_runner = offence.stream().anyMatch(turnEntity -> turnEntity.getBase() == TurnEntity.Base.FIRST_BASE.getValue());
        boolean is_second_runner = offence.stream().anyMatch(turnEntity -> turnEntity.getBase() == TurnEntity.Base.SECOND_BASE.getValue());
        boolean is_third_runner = offence.stream().anyMatch(turnEntity -> turnEntity.getBase() == TurnEntity.Base.THIRD_BASE.getValue());

        boolean noRequiredAction = lastAction == null || lastAction.isProceed() || lastAction.isStandalone();
        if (noRequiredAction) {
            availableActions.setBatter(ActionUtils.getActionsForBatter());
            if (is_third_runner) {
                RunnerActionDto dto = new RunnerActionDto();
                dto.setHomeBase(ActionUtils.getActionsForAdvanceRunner());
                availableActions.setThirdBaseRunner(dto);
            }
            if (is_second_runner) {
                RunnerActionDto dto = new RunnerActionDto();
                dto.setThirdBase(ActionUtils.getActionsForAdvanceRunner());
                dto.setHomeBase(ActionUtils.getActionsForAdvanceRunner());
                availableActions.setSecondBaseRunner(dto);
            }
            if (is_first_runner) {
                RunnerActionDto dto = new RunnerActionDto();
                dto.setSecondBase(ActionUtils.getActionsForAdvanceRunner());
                dto.setThirdBase(is_third_runner ? null : ActionUtils.getActionsForAdvanceRunner());
                dto.setHomeBase(is_third_runner ? null : ActionUtils.getActionsForAdvanceRunner());
                availableActions.setFirstBaseRunner(dto);
            }
        } else {
            if (lastAction.getLinkedAction() == null) {
                if (is_third_runner) {
                    RunnerActionDto dto = new RunnerActionDto();
                    if (lastAction.getDistance() < 3) {
                        dto.setThirdBase(ActionUtils.getActionsForHoldRunner());
                    }
                    dto.setHomeBase(ActionUtils.getActionsForAdvanceRunner());
                    availableActions.setThirdBaseRunner(dto);
                } else if (is_second_runner) {
                    RunnerActionDto dto = new RunnerActionDto();
                    if (lastAction.getDistance() < 2) {
                        dto.setSecondBase(ActionUtils.getActionsForHoldRunner());
                    }
                    if (lastAction.getDistance() < 3) {
                        dto.setThirdBase(is_third_runner ? null : ActionUtils.getActionsForAdvanceRunner());
                    }
                    dto.setHomeBase(is_third_runner ? null : ActionUtils.getActionsForAdvanceRunner());
                    availableActions.setSecondBaseRunner(dto);
                } else if (is_first_runner) {
                    RunnerActionDto dto = new RunnerActionDto();
                    if (lastAction.getDistance() < 2) {
                        dto.setSecondBase(ActionUtils.getActionsForAdvanceRunner());
                    }
                    if (lastAction.getDistance() < 3) {
                        dto.setThirdBase(is_third_runner ? null : ActionUtils.getActionsForAdvanceRunner());
                    }
                    dto.setHomeBase(is_third_runner ? null : ActionUtils.getActionsForAdvanceRunner());
                    availableActions.setFirstBaseRunner(dto);
                } else {
                    throw new RuntimeException("Backend Logic Bug: we have not-standalone action and no runners");
                }
            } else {
                List<Integer> occupiedBases = new ArrayList<>();
                occupiedBases.add(lastAction.getTurn().getBase() + lastAction.getDistance());

                List<Integer> runnersWithActions = new ArrayList<>();
                ActionEntity linkedAction = lastAction.getLinkedAction();
                while (linkedAction != null) {
                    occupiedBases.add(linkedAction.getTurn().getBase() + linkedAction.getDistance());
                    runnersWithActions.add(linkedAction.getTurn().getBase());
                    linkedAction = linkedAction.getLinkedAction();
                }

                if (is_second_runner && !runnersWithActions.contains(2)) {
                    RunnerActionDto dto = new RunnerActionDto();
                    if (!occupiedBases.contains(2)) {
                        dto.setSecondBase(ActionUtils.getActionsForHoldRunner());
                    }
                    if (!occupiedBases.contains(3)) {
                        dto.setThirdBase(ActionUtils.getActionsForAdvanceRunner());
                        dto.setHomeBase(ActionUtils.getActionsForAdvanceRunner());
                    }
                    availableActions.setSecondBaseRunner(dto);
                } else if (is_first_runner && !runnersWithActions.contains(1)) {
                    RunnerActionDto dto = new RunnerActionDto();
                    if (!occupiedBases.contains(2)) {
                        dto.setSecondBase(ActionUtils.getActionsForAdvanceRunner());
                    }
                    if (!occupiedBases.contains(2) && !occupiedBases.contains(3)) {
                        dto.setThirdBase(ActionUtils.getActionsForAdvanceRunner());
                        dto.setHomeBase(ActionUtils.getActionsForAdvanceRunner());
                    }
                    availableActions.setFirstBaseRunner(dto);
                }
            }
        }

        return availableActions;
    }

    /**
     * Check if the action type is allowed.
     *
     * @param base the base involved in the action
     * @param actionType the type of action
     * @param allowedActions the allowed actions for the current turn
     * @return true if the action is allowed, false otherwise
     */
    private boolean isActionAllowed(int base, ActionEntity.Type actionType, ActionGetDto allowedActions) {
        List<ActionEntity.Type> allowed = new ArrayList<>();

        if (base == 0 && allowedActions.getBatter() != null) {
            allowed.addAll(allowedActions.getBatter().getOut().stream().map(ButtonDto::getActionType).toList());
            allowed.addAll(allowedActions.getBatter().getSafe().stream().map(ButtonDto::getActionType).toList());
            allowed.addAll(allowedActions.getBatter().getError().stream().map(ButtonDto::getActionType).toList());
        } else if (base == 1 && allowedActions.getFirstBaseRunner() != null) {
            allowed = extractRunnerActions(allowedActions.getFirstBaseRunner());
        } else if (base == 2 && allowedActions.getSecondBaseRunner() != null) {
            allowed = extractRunnerActions(allowedActions.getSecondBaseRunner());
        } else if (base == 3 && allowedActions.getThirdBaseRunner() != null) {
            allowed = extractRunnerActions(allowedActions.getThirdBaseRunner());
        } else {
            return false;
        }

        return allowed.contains(actionType);
    }

    /**
     * Extract actions from runner actions.
     *
     * @param runnerActions the runner actions
     * @return a list of allowed action types
     */
    private List<ActionEntity.Type> extractRunnerActions(RunnerActionDto runnerActions) {
        List<ActionEntity.Type> actions = new ArrayList<>();
        if (runnerActions.getFirstBase() != null) {
            actions.addAll(runnerActions.getFirstBase().getOut().stream().map(ButtonDto::getActionType).toList());
            actions.addAll(runnerActions.getFirstBase().getSafe().stream().map(ButtonDto::getActionType).toList());
            actions.addAll(runnerActions.getFirstBase().getError().stream().map(ButtonDto::getActionType).toList());
        }
        if (runnerActions.getSecondBase() != null) {
            actions.addAll(runnerActions.getSecondBase().getOut().stream().map(ButtonDto::getActionType).toList());
            actions.addAll(runnerActions.getSecondBase().getSafe().stream().map(ButtonDto::getActionType).toList());
            actions.addAll(runnerActions.getSecondBase().getError().stream().map(ButtonDto::getActionType).toList());
        }
        if (runnerActions.getThirdBase() != null) {
            actions.addAll(runnerActions.getThirdBase().getOut().stream().map(ButtonDto::getActionType).toList());
            actions.addAll(runnerActions.getThirdBase().getSafe().stream().map(ButtonDto::getActionType).toList());
            actions.addAll(runnerActions.getThirdBase().getError().stream().map(ButtonDto::getActionType).toList());
        }
        if (runnerActions.getHomeBase() != null) {
            actions.addAll(runnerActions.getHomeBase().getOut().stream().map(ButtonDto::getActionType).toList());
            actions.addAll(runnerActions.getHomeBase().getSafe().stream().map(ButtonDto::getActionType).toList());
            actions.addAll(runnerActions.getHomeBase().getError().stream().map(ButtonDto::getActionType).toList());
        }
        return actions;
    }

    /**
     * Execute the given action.
     *
     * @param actionType the type of action to be executed
     * @param base the base involved in the action
     * @param responsible the entity(ies) of defence player that was involved in the action
     * @param turn the current turn
     */
    private void executeAction(ActionEntity.Type actionType, int base, List<ResponsibleDto> responsible, TurnEntity turn) {
        TurnService service = new TurnService();

        if (ActionEntity.atBat().contains(actionType)) {
            if (ActionEntity.Type.BALL.equals(actionType)) {
                BallCommand command = new BallCommand(service, turn);
                command.execute();
            } else if (ActionEntity.Type.STRIKE.equals(actionType)) {
                StrikeCommand command = new StrikeCommand(service, turn);
                command.execute();
            } else if (ActionEntity.Type.FOUL.equals(actionType)) {
                FoulCommand command = new FoulCommand(service, turn);
                command.execute();
            }
        } else if (ActionEntity.out().contains(actionType)) {
            if (ActionEntity.Type.ASSISTED_OUT.equals(actionType)) {
                AssistedOutCommand command = new AssistedOutCommand(service, turn, base, responsible);
                command.execute();
            }
        } else if (ActionEntity.safe().contains(actionType)) {
            if (ActionEntity.Type.BASE_ON_BALLS.equals(actionType)) {
                BaseOnBallsCommand command = new BaseOnBallsCommand(service, turn);
                command.execute();
            } else if (ActionEntity.Type.HIT_SINGLE.equals(actionType)) {
                HitSingleCommand command = new HitSingleCommand(service, turn);
                command.execute();
            } else if (ActionEntity.Type.HIT_DOUBLE.equals(actionType)) {
                HitDoubleCommand command = new HitDoubleCommand(service, turn);
                command.execute();
            } else if (ActionEntity.Type.HIT_TRIPLE.equals(actionType)) {
                HitTripleCommand command = new HitTripleCommand(service, turn);
                command.execute();
            } else if (ActionEntity.Type.HOME_RUN.equals(actionType)) {
                HomeRunCommand command = new HomeRunCommand(service, turn);
                command.execute();
            } else if (ActionEntity.Type.HOLD.equals(actionType)) {
                HoldCommand command = new HoldCommand(service, turn, base);
                command.execute();
            }
        }
    }
}