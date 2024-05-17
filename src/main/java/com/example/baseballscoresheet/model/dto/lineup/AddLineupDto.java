package com.example.baseballscoresheet.model.dto.lineup;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddLineupDto {

    private Long teamId;

    private List<AddPlayerToLineupDto> playerDtoSet;

}
