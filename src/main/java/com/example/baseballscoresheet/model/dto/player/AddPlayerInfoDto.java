package com.example.baseballscoresheet.model.dto.player;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPlayerInfoDto {

    @NotBlank(message = "Firstname is obligatory")
    @Size(max = 70, message = "Firstname must not exceed 70 characters")
    private String firstName;

    @NotBlank(message = "Lastname is obligatory")
    @Size(max = 70, message = "Lastname must not exceed 70 characters")
    private String lastName;

    @NotNull
    @Min(value = 00000000)
    @Max(value = 99999999)
    private Integer passnumber;
}
