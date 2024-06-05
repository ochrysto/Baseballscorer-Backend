package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.exceptionHandling.NotImplementedError;
import org.springframework.stereotype.Component;


@Component
public class AdvancedByRuleCommand extends Command {
    @Override
    public void execute() {
        throw new NotImplementedError("The 'Advanced By Rule' game action is not yet implemented.");
    }
}
