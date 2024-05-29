package com.example.baseballscoresheet.model.dtos.action;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunnerActionDto {
    private ActionDto firstBase;
    private ActionDto secondBase;
    private ActionDto thirdBase;
    private ActionDto homeBase;
}
