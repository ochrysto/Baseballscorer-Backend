package com.example.baseballscoresheet.model.dtos.player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerGetDto {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer passnumber;
}
