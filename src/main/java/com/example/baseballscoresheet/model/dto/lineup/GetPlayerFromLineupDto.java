package com.example.baseballscoresheet.model.dto.lineup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPlayerFromLineupDto {

    private String playerName;

    private Integer jerseyNr;

    private String position;

    public void setPlayerName(String firstName, String lastName) {
        this.playerName = firstName + " " + lastName;
    }
}
