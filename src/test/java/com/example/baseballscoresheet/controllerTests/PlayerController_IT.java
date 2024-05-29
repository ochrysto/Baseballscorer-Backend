package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.PlayerEntity;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlayerController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/player"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void findAllPlayers() throws Exception {

        var player = new PlayerEntity();
        player.setId(1L);
        player.setFirstName("Test Player");
        player.setLastName("Test Player");
        player.setPassnumber(123);

        this.playerRepository.save(player);

        this.mockMvc.perform(get("/player"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is((1))))
                .andExpect(jsonPath("$[0].firstName", is("Test Player")))
                .andExpect(jsonPath("$[0].lastName", is("Test Player")))
                .andExpect(jsonPath("$[0].passnumber", is(123)))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
