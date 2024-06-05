package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.exceptionHandling.NotImplementedError;
import org.springframework.stereotype.Component;

@Component
public class StolenBaseCommand extends Command {
    @Override
    public void execute() {
        throw new NotImplementedError("The 'Stolen Base' game action is not yet implemented.");
    }
}