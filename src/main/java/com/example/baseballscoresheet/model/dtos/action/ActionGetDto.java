package com.example.baseballscoresheet.model.dtos.action;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionGetDto {
    private ActionDto batter;
    private RunnerActionDto firstBaseRunner;
    private RunnerActionDto secondBaseRunner;
    private RunnerActionDto thirdBaseRunner;
}
