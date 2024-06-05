package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.exceptionHandling.NotImplementedError;
import org.springframework.stereotype.Component;



@Component
public class AssistedErrorCommand extends Command {
    @Override
    public void execute() {
        throw new NotImplementedError("The 'Assisted Error' game action is not yet implemented.");
    }
}
