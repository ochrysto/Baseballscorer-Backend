package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.exceptionHandling.NotImplementedError;
import org.springframework.stereotype.Component;



@Component
public class OutByRuleCommand extends Command {
    @Override
    public void execute() {
        throw new NotImplementedError("The 'Out By Rule' game action is not yet implemented.");
    }
}
