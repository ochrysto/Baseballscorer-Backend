package com.example.baseballscoresheet.model.dtos.league;

import com.example.baseballscoresheet.model.dtos.association.GetAssociationDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetLeagueDto {
    private long id;
    private String name;
    private GetAssociationDto association;
}
