package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.exceptionHandling.ResourceNotFoundException;
import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TurnService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TurnRepository turnRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private InningRepository inningRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public static final int MAX_STRIKES = 3;
    public static final int MAX_BALLS = 4;
    public static final int FIRST_BASE = 1;
    public static final int SECOND_BASE = 2;
    public static final int THIRD_BASE = 3;
    public static final int HOME_BASE = 4;
    @Autowired
    private LineupTeamPlayerRepository lineupTeamPlayerRepository;

    public GameEntity getGame(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(() ->
                new ResourceNotFoundException("Game with id `" + gameId + "` not found")
        );
    }

    public TurnEntity getLastTurn(GameEntity game) {
        return turnRepository.findFirstByInningGameOrderByIdDesc(game)
                .orElseThrow(() -> new ResourceNotFoundException("No turn for the game with id `" + game.getId() + "` found"));
    }

    public TurnEntity getLastTurnOrElseNull(GameEntity game) {
        return turnRepository.findFirstByInningGameOrderByIdDesc(game).orElse(null);
    }

    public ActionEntity getLastAction(TurnEntity turn) {
        ActionEntity lastAction = actionRepository.findFirstByTurnOrderByIdDesc(turn);
        while (lastAction != null) {
            ActionEntity linkedAction = actionRepository.findByLinkedAction(lastAction);
            if (linkedAction == null) {
                break;
            }
            lastAction = linkedAction;
        }
        return lastAction;
    }

    public TurnEntity getRunnerByBase(TurnEntity turn, int base) {
        return turnRepository.findByInningAndBaseAndCurrentStatus(turn.getInning(), base, TurnEntity.Status.ON_BASE)
                .orElseThrow(() -> new ResourceNotFoundException("No runner for the inning with id `" + turn.getInning().getId() + "` and the base " + base + " found"));
    }

    @Transactional
    public TurnEntity increaseBallsCount(TurnEntity turn) {
        turn.setBalls(turn.getBalls() + 1);
        return turnRepository.save(turn);
    }

    @Transactional
    public void increaseStrikeCount(TurnEntity turn) throws Exception {
        if (turn.getStrikes() >= MAX_STRIKES) {
            throw new Exception("The maximum number of strikes has already been reached.");
        }
        turn.setStrikes(turn.getStrikes() + 1);
        if (turn.getStrikes() == MAX_STRIKES) {
            turn.setCurrentStatus(TurnEntity.Status.IS_OUT);
            actionRepository.save(new ActionEntity(turn, ActionEntity.Type.STRIKEOUT));
        }
        turnRepository.save(turn);
    }

    @Transactional
    public void increaseFoulCount(TurnEntity turn) {
        if (turn.getStrikes() < MAX_STRIKES - 1) {
            turn.setStrikes(turn.getStrikes() + 1);
        }
        turnRepository.save(turn);
        actionRepository.save(new ActionEntity(turn, ActionEntity.Type.FOUL));
    }

    @Transactional
    public void moveBatterToBase(TurnEntity turn, int base) {
        turn.setBase(base);
        turn.setCurrentStatus(TurnEntity.Status.ON_BASE);
        turnRepository.save(turn);
    }

    @Transactional
    public void batterHomeRun(TurnEntity turn) {
        moveBatterToBase(turn, HOME_BASE);
        turn.setCurrentStatus(TurnEntity.Status.RUN);
        turnRepository.save(turn);
    }

    public TurnEntity getBatter(TurnEntity turn) {
        return turnRepository.findByInningAndBaseAndCurrentStatus(turn.getInning(), 0, TurnEntity.Status.AT_BAT)
                .orElseThrow(() -> new ResourceNotFoundException("No batter for the inning with id `" + turn.getInning().getId() + "` found"));
    }

    public List<TurnEntity> getActiveRunners(GameEntity game) {
        InningEntity activeInning = inningRepository.findFirstByGameOrderByIdDesc(game)
                .orElseThrow(() -> new ResourceNotFoundException("No active inning for the game with id `" + game.getId() + "` found"));
        return turnRepository.findByInningAndCurrentStatus(activeInning, TurnEntity.Status.ON_BASE);
    }

    /**
     * Create new turn in the current inning, also new better comes in the game.
     *
     * @param turn the current turn
     */
    @Transactional
    public TurnEntity createNewTurn(TurnEntity turn) {
        // TODO: check if more than 3 outs
        InningEntity inning = turn.getInning();
        long nextId = turnRepository.countByInning(inning) + 1;
        LineupTeamPlayerEntity batter = lineupTeamPlayerRepository.findById(nextId)
                .orElseThrow(() -> new ResourceNotFoundException("Batter with id `" + nextId + "` not found"));
        return turnRepository.save(new TurnEntity(batter, inning, 0, TurnEntity.Status.AT_BAT));
    }

    @Transactional
    public ActionEntity createNewAction(ActionEntity action) {
        return actionRepository.save(action);
    }

    @Transactional
    public TurnEntity updateTurn(TurnEntity turn) {
        return this.turnRepository.save(turn);
    }

    @Transactional
    public ActionEntity updateAction(ActionEntity action) {
        return this.actionRepository.save(action);
    }

    @Transactional
    public void advanceRunners(GameEntity game, ActionEntity baseAction) {
        List<TurnEntity> runners = getActiveRunners(game);
        if (!runners.isEmpty()) {
            baseAction.setStandalone(false);
            actionRepository.save(baseAction);
            ActionEntity prevAction = baseAction;
            for (TurnEntity runner : runners) {
                TurnEntity previousRunner = runners.stream()
                        .filter(r -> r.getBase() == runner.getBase() - 1)
                        .findFirst()
                        .orElse(null);
                if (previousRunner != null) {
                    prevAction = actionRepository.save(new ActionEntity(
                            runner, ActionEntity.Type.ADVANCED_BY_BATTER, ActionEntity.Place.forBase(runner.getBase()), 1, prevAction, false));
                    // move batter / runner and change status
                    runner.setBase(runner.getBase() + 1);
                    runner.setCurrentStatus(runner.getBase() < 4 ? TurnEntity.Status.ON_BASE : TurnEntity.Status.RUN);
                    turnRepository.save(runner);
                }
            }
        }
    }

    public void updateTurnsAndActions(ActionEntity lastAction, TurnEntity currentTurn) {
        List<TurnEntity> offence = this.getActiveRunners(currentTurn.getInning().getGame());
        boolean is_first_runner = offence.stream().anyMatch(turnEntity -> turnEntity.getBase() == TurnEntity.Base.FIRST_BASE.getValue());
        boolean is_second_runner = offence.stream().anyMatch(turnEntity -> turnEntity.getBase() == TurnEntity.Base.SECOND_BASE.getValue());
        if (!is_first_runner && !is_second_runner) {
            saveLinkedActions(lastAction);
            this.createNewTurn(currentTurn);
        }
    }

    private void saveLinkedActions(ActionEntity action) {
        while (action != null) {
            TurnEntity turnToUpdate = action.getTurn();
            turnToUpdate.setBase(turnToUpdate.getBase() + action.getDistance());

            if (turnToUpdate.getBase() > 0 && turnToUpdate.getBase() < 4) {
                turnToUpdate.setCurrentStatus(TurnEntity.Status.ON_BASE);
            } else if (turnToUpdate.getBase() == 4) {
                turnToUpdate.setCurrentStatus(TurnEntity.Status.RUN);
            }

            if (ActionEntity.out().contains(action.getType())) {
                turnToUpdate.setCurrentStatus(TurnEntity.Status.IS_OUT);
            }

            this.updateTurn(turnToUpdate);
            action.setProceed(true);
            this.updateAction(action);
            action = action.getLinkedAction();
        }
    }

    public List<TurnEntity> getTurnsByPlayerId(long playerId) {
        return this.turnRepository.findTurnEntitiesByLineupTeamPlayer_Id(playerId);
    }

    public List<TurnEntity> getTurnsByInning(long inningId) {
        return this.turnRepository.findTurnEntitiesByInning_IdOrderById(inningId);
    }

    public List<ActionEntity> getActionsByTurn(long turnId) {
        return actionRepository.findAllByTurn_IdOrderByIdDesc(turnId);
    }

    public InningEntity createFistInning(GameEntity game) {
        InningEntity inning = new InningEntity();
        inning.setGame(game);
        inning.setInning(1);  // we always start a game with inning = 1
        inning.setOuts(0);
        inning.setBattingTeam(InningEntity.Team.AWAY);
        return inningRepository.save(inning);
    }

    public TurnEntity createFirstTurn(GameEntity game) {
        InningEntity inning = this.createFistInning(game);
//        long position = 1;  // we always start with a position 1
        LineupTeamPlayerEntity batter = lineupTeamPlayerRepository.findFirstByLineup_Game_IdAndLineup_Team_IdOrderByPositionIdAsc(game.getId(), game.getGuest().getId())  // we always start with first guest team batter
                .orElseThrow(() -> new ResourceNotFoundException("Lineup team player not found"));
//        LineupTeamPlayerEntity batter = lineupTeamPlayerRepository.findLineupTeamPlayerEntityByPosition_Id(position)
//                .orElseThrow(() -> new ResourceNotFoundException("Lineup team player with position `" + position + "` not found"));
        return this.turnRepository.save(new TurnEntity(batter, inning, 0, TurnEntity.Status.AT_BAT));
    }
}
