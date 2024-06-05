package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.exceptionHandling.NotImplementedError;
import org.springframework.stereotype.Component;

@Component
public class PassedBallCommand extends Command {
    @Override
    public void execute() {
        throw new NotImplementedError("The 'Passed Ball' game action is not yet implemented.");
    }
}
