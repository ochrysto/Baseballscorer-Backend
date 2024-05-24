package com.example.baseballscoresheet.model.dtos.action;

import com.example.baseballscoresheet.model.dtos.button.ButtonDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ActionDto {
    private List<ButtonDto> out;
    private List<ButtonDto> safe;
    private List<ButtonDto> error;
}
