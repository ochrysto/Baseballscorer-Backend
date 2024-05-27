package com.example.baseballscoresheet.command;

import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class HoldCommand extends Command {
    @Setter
    private int base;

    @Override
    public void execute() {

    }
}
