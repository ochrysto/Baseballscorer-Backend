package com.example.baseballscoresheet.model.dtos.league;

import com.example.baseballscoresheet.model.dtos.association.AssociationGetDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LeagueGetDto {
    private long id;
    private String name;
    private AssociationGetDto association;
}
