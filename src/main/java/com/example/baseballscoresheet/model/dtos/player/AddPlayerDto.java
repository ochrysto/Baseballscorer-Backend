package com.example.baseballscoresheet.model.dtos.player;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPlayerDto {

    @NotBlank(message = "Players first name is mandatory")
    @Size(max = 70, message = "Firstname must not exceed 70 characters")
    private String firstName;

    @NotBlank(message = "Players last name is mandatory")
    @Size(max = 70, message = "Lastname must not exceed 70 characters")
    private String lastName;

    @NotNull
    @Min(value = 0)
    @Max(value = 99999999)
    private Integer passnumber;
}
