package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.exceptionHandling.NotImplementedError;
import org.springframework.stereotype.Component;


@Component
public class ErrorCommand extends Command {
    @Override
    public void execute() {
        throw new NotImplementedError("The 'Error' game action is not yet implemented.");
    }
}
