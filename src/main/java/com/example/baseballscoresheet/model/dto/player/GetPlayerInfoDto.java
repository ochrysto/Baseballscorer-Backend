package com.example.baseballscoresheet.model.dto.player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPlayerInfoDto {
    private Long id;

    private String firstName;

    private String lastName;

    private Integer passnumber;
}
