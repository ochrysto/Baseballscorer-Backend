package com.example.baseballscoresheet.command;

public class BallCommand implements Command {
    private final TurnService service;
    private final Turn turn;

    public BallCommand(TurnService service, Turn turn) {
        this.service = service;
        this.turn = turn;
    }

    @Override
    public void execute() {
        if (turn.getBalls() >= TurnService.MAX_BALLS) {
            throw new BadRequestException("The maximum number of balls has already been reached.");
        }

        service.executeWithTransaction(() -> {
            turn.setBalls(turn.getBalls() + 1);
            turn.save();
            Action action = new Action(turn, Action.Type.BALL);
            action.save();
            service.advanceRunners(turn.getFrame().getGame(), action);
        });
    }
}

public class StrikeCommand implements Command {
    private final TurnService service;
    private final Turn turn;

    public StrikeCommand(TurnService service, Turn turn) {
        this.service = service;
        this.turn = turn;
    }

    @Override
    public void execute() {
        service.increaseStrikeCount(turn);
    }
}{
}
