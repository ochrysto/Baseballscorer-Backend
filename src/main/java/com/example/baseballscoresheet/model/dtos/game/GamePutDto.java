package com.example.baseballscoresheet.model.dtos.game;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class GamePutDto {

    @Min(value = 2)
    @Max(value = 35)
    private Integer innings;

    @NotNull(message = "Number of attendance is mandatory")
    private Integer attendance;

    // HH:MM:ss.ffffff
    // Stunden:Minuten:Sekunden:Mikrosekunden
    private LocalTime startTime;

    private LocalTime endTime;

    private Long durationInMinutes;

}
