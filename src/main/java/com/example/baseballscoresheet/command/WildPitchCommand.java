package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.exceptionHandling.NotImplementedError;
import org.springframework.stereotype.Component;

@Component
public class WildPitchCommand extends Command {
    @Override
    public void execute() {
        throw new NotImplementedError("The 'Wild Pitch' game action is not yet implemented.");
    }
}