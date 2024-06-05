package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.exceptionHandling.NotImplementedError;
import org.springframework.stereotype.Component;

@Component
public class SacrificeFlyCommand extends Command {
    @Override
    public void execute() {
        throw new NotImplementedError("The 'Sacrifice Fly' game action is not yet implemented.");
    }
}
