package com.example.baseballscoresheet.model.dtos.player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPlayerInfoForLineUpDto {

    private Long id;

    // vorname + nachname
    private String name;

    private Integer passnumber;
}
