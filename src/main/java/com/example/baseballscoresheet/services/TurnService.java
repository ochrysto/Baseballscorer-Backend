package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurnService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TurnEntityRepository turnRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private FrameRepository frameRepository;

    @Autowired
    private BatterRepository batterRepository;

    private static final int MAX_STRIKES = 3;
    private static final int MAX_BALLS = 4;
    private static final int FIRST_BASE = 1;
    private static final int SECOND_BASE = 2;
    private static final int THIRD_BASE = 3;
    private static final int HOME_BASE = 4;

    public Game getGame(int gameId) {
        return gameRepository.findById(gameId).orElseThrow(() ->
                new NotFoundException("Game with id `" + gameId + "` not found")
        );
    }

    public Turn getLastTurn(Game game) {
        return turnRepository.findFirstByFrameGameOrderByIdDesc(game)
                .orElseThrow(() -> new NotFoundException("No turn for the game with id `" + game.getId() + "` found"));
    }

    public Action getLastAction(Turn turn) {
        Action lastAction = actionRepository.findFirstByTurnOrderByIdDesc(turn);
        while (lastAction != null) {
            Action linkedAction = actionRepository.findByLinkedAction(lastAction);
            if (linkedAction == null) {
                break;
            }
            lastAction = linkedAction;
        }
        return lastAction;
    }

    public Turn getRunnerByBase(Turn turn, int base) {
        return turnRepository.findByFrameAndBaseAndCurrentStatus(
                        turn.getFrame(), base, Turn.Status.ON_BASE)
                .orElseThrow(() -> new NotFoundException(
                        "No turn for the frame with id `" + turn.getFrame().getId() + "` and the base " + base + " found"));
    }

    @Transactional
    public void increaseStrikeCount(Turn turn) {
        if (turn.getStrikes() >= MAX_STRIKES) {
            throw new BadRequestException("The maximum number of strikes has already been reached.");
        }
        turn.setStrikes(turn.getStrikes() + 1);
        if (turn.getStrikes() == MAX_STRIKES) {
            turn.setCurrentStatus(Turn.Status.IS_OUT);
            actionRepository.save(new Action(turn, Action.Type.STRIKEOUT));
        }
        turnRepository.save(turn);
    }

    @Transactional
    public void increaseFoulCount(Turn turn) {
        if (turn.getStrikes() < MAX_STRIKES - 1) {
            turn.setStrikes(turn.getStrikes() + 1);
        }
        turnRepository.save(turn);
        actionRepository.save(new Action(turn, Action.Type.FOUL));
    }

    @Transactional
    public void moveBatterToBase(Turn turn, int base) {
        turn.setBase(base);
        turn.setCurrentStatus(Turn.Status.ON_BASE);
        turnRepository.save(turn);
    }

    @Transactional
    public void batterHomeRun(Turn turn) {
        moveBatterToBase(turn, HOME_BASE);
        turn.setCurrentStatus(Turn.Status.RUN);
        turnRepository.save(turn);
    }

    public List<Turn> getActiveRunners(Game game) {
        Frame activeFrame = frameRepository.findFirstByGameOrderByIdDesc(game)
                .orElseThrow(() -> new NotFoundException("No active frame for the game with id `" + game.getId() + "` found"));
        return turnRepository.findByFrameAndCurrentStatus(activeFrame, Turn.Status.ON_BASE);
    }

    @Transactional
    public void createNewTurn(Turn turn) {
        Frame frame = turn.getFrame();
        int nextId = turnRepository.countByFrame(frame) + 1;
        Batter batter = batterRepository.findById(nextId)
                .orElseThrow(() -> new NotFoundException("Batter with id `" + nextId + "` not found"));
        turnRepository.save(new Turn(batter, frame, 0, Turn.Status.AT_BAT));
    }

    @Transactional
    public void advanceRunners(Game game, Action baseAction) {
        List<Turn> runners = getActiveRunners(game);
        if (!runners.isEmpty()) {
            baseAction.setStandalone(false);
            actionRepository.save(baseAction);
            Action prevAction = baseAction;
            for (Turn runner : runners) {
                Turn previousRunner = runners.stream()
                        .filter(r -> r.getBase() == runner.getBase() - 1)
                        .findFirst()
                        .orElse(null);
                if (previousRunner != null) {
                    prevAction = actionRepository.save(new Action(
                            runner, Action.Type.ADVANCED_BY_BATTER, 1,
                            Action.Place.forBase(runner.getBase()), prevAction, false));
                }
            }
        }
    }
}
