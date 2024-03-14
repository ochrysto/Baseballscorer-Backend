package com.example.baseballscoresheet;

import com.example.baseballscoresheet.enums.POSITION;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BaseballScoresheetApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseballScoresheetApplication.class, args);
        System.out.println(POSITION.FIRST_BASEMAN.getId());
    }
}
