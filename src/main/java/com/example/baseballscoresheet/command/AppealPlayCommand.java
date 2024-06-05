package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.exceptionHandling.NotImplementedError;
import org.springframework.stereotype.Component;


@Component
public class AppealPlayCommand extends Command {
    @Override
    public void execute() {
        throw new NotImplementedError("The 'Appeal Play' game action is not yet implemented.");
    }
}
