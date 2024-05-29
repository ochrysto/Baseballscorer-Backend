package com.example.baseballscoresheet.model.dtos.action;

import com.example.baseballscoresheet.model.dtos.diamond.DiamondDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OffensiveActionsDto {
    private String name;
    private int passNumber;
    private boolean isAtBat;
    private List<DiamondDto> actions;
}
