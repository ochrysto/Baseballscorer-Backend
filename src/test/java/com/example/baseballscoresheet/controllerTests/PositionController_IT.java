package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PositionController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/positions"))
                .andExpect(status().isUnauthorized());
    }
}
