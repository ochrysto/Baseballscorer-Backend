package com.example.baseballscoresheet.model.dtos.game;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class UpdateGameDto {

    private Integer innings;

    private Integer attendance;

    // HH:MM:ss.ffffff
    // Stunden:Minuten:Sekunden:Mikrosekunden
    private LocalTime startTime;

    private LocalTime endTime;

    private Long durationInMinutes;

}
