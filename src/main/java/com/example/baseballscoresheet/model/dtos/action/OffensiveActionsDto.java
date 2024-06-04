package com.example.baseballscoresheet.model.dtos.action;

import com.example.baseballscoresheet.model.dtos.diamond.DiamondDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OffensiveActionsDto {
    private String firstName;
    private String lastName;
    private int jerseyNr;
    private boolean isAtBat;
    private DiamondDto diamond;
}
