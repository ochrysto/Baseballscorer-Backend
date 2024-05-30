package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.PositionEntity;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PositionController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/positions"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void findAllPositions() throws Exception {

        var position = new PositionEntity();
        position.setId(1L);
        position.setDescription("Test Position");

        this.positionRepository.save(position);

        this.mockMvc.perform(get("/positions"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("Test Position")))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
