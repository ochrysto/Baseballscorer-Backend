package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.ScorerEntity;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScorerController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/scorer"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void findAllScorers() throws Exception {

        var scorer = new ScorerEntity();
        scorer.setId(1L);
        scorer.setPassnumber(123L);
        scorer.setFirstName("Test Scorer");
        scorer.setLastName("Test Scorer");

        this.scorerRepository.save(scorer);

        this.mockMvc.perform(get("/scorer"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].scorerId", is((1))))
                .andExpect(jsonPath("$[0].passnumber", is(123)))
                .andExpect(jsonPath("$[0].name", is("Test Scorer Test Scorer")))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
