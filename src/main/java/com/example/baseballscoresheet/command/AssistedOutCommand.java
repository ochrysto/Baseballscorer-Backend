package com.example.baseballscoresheet.command;

import com.example.baseballscoresheet.model.dtos.responsible.ResponsibleDto;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Component
public class AssistedOutCommand extends Command {
    private int base;
    private List<ResponsibleDto> responsible;

    @Override
    public void execute() {

    }
}
