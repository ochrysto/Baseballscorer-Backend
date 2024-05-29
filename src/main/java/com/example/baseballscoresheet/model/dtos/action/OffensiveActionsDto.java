package com.example.baseballscoresheet.model.dtos.action;

import com.example.baseballscoresheet.model.dtos.diamond.DiamondDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OffensiveActionsDto {
    private String firstName;
    private String lastName;
    private int jerseyNumber;
    private boolean isAtBat;
    private List<DiamondDto> actions;
}
