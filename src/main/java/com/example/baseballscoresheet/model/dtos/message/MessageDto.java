package com.example.baseballscoresheet.model.dtos.message;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDto {
    String msg;

    public MessageDto(String msg) {
        this.msg = msg;
    }
}
