package com.example.baseballscoresheet.services;

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

    private static final int MAX_STRIKES = 3;
    public static final int MAX_BALLS = 4;
    private static final int FIRST_BASE = 1;
    private static final int SECOND_BASE = 2;
    private static final int THIRD_BASE = 3;
    private static final int HOME_BASE = 4;

    public GameEntity getGame(Long gameId) throws Exception {
        return gameRepository.findById(gameId).orElseThrow(() ->
                new Exception("Game with id `" + gameId + "` not found")
        );
    }

    public TurnEntity getLastTurn(GameEntity game) throws Exception {
        return turnRepository.findFirstByInningGameOrderByIdDesc(game)
                .orElseThrow(() -> new Exception("No turn for the game with id `" + game.getId() + "` found"));
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

    public TurnEntity getRunnerByBase(TurnEntity turn, int base) throws Exception {
        return turnRepository.findByInningAndBaseAndCurrentStatus(
                        turn.getInning(), base, TurnEntity.Status.ON_BASE.toString())
                .orElseThrow(() -> new Exception(
                        "No turn for the inning with id `" + turn.getInning().getId() + "` and the base " + base + " found"));
    }

    @Transactional
    public void increaseStrikeCount(TurnEntity turn) throws Exception {
        if (turn.getStrikes() >= MAX_STRIKES) {
            throw new Exception("The maximum number of strikes has already been reached.");
        }
        turn.setStrikes(turn.getStrikes() + 1);
        if (turn.getStrikes() == MAX_STRIKES) {
            turn.setCurrentStatus(TurnEntity.Status.IS_OUT.toString());
            actionRepository.save(new ActionEntity(turn, ActionEntity.Type.STRIKEOUT.toString()));
        }
        turnRepository.save(turn);
    }

    @Transactional
    public void increaseFoulCount(TurnEntity turn) {
        if (turn.getStrikes() < MAX_STRIKES - 1) {
            turn.setStrikes(turn.getStrikes() + 1);
        }
        turnRepository.save(turn);
        actionRepository.save(new ActionEntity(turn, ActionEntity.Type.FOUL.toString()));
    }

    @Transactional
    public void moveBatterToBase(TurnEntity turn, int base) {
        turn.setBase(base);
        turn.setCurrentStatus(TurnEntity.Status.ON_BASE.toString());
        turnRepository.save(turn);
    }

    @Transactional
    public void batterHomeRun(TurnEntity turn) {
        moveBatterToBase(turn, HOME_BASE);
        turn.setCurrentStatus(TurnEntity.Status.RUN.toString());
        turnRepository.save(turn);
    }

    public List<TurnEntity> getActiveRunners(GameEntity game) throws Exception {
        InningEntity activeInning = inningRepository.findFirstByGameOrderByIdDesc(game)
                .orElseThrow(() -> new Exception("No active inning for the game with id `" + game.getId() + "` found"));
        return turnRepository.findByInningAndCurrentStatus(activeInning, TurnEntity.Status.ON_BASE.toString());
    }

    @Transactional
    public void createNewTurn(TurnEntity turn) throws Exception {
        InningEntity inning = turn.getInning();
        long nextId = turnRepository.countByInning(inning) + 1;
        PlayerEntity batter = playerRepository.findById(nextId)
                .orElseThrow(() -> new Exception("Batter with id `" + nextId + "` not found"));
        turnRepository.save(new TurnEntity(batter, inning, 0, TurnEntity.Status.AT_BAT.toString()));
    }

    @Transactional
    public void advanceRunners(GameEntity game, ActionEntity baseAction) throws Exception {
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
                            runner, ActionEntity.Type.ADVANCED_BY_BATTER.toString(), ActionEntity.Place.forBase(runner.getBase()).toString(), 1, prevAction, false));
                }
            }
        }
    }
}
