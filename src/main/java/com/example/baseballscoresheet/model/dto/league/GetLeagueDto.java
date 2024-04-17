package com.example.baseballscoresheet.model.dto.league;

import com.example.baseballscoresheet.model.dto.association.GetAssociationDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetLeagueDto {

    private String name;

    private GetAssociationDto association;
}
