package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.UmpireEntity;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UmpireController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/umpire"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void findAllUmpires() throws Exception {

        var umpire = new UmpireEntity();
        umpire.setId(1L);
        umpire.setPassnumber(123L);
        umpire.setFirstName("Test Umpire");
        umpire.setLastName("Test Umpire");

        this.umpireRepository.save(umpire);

        this.mockMvc.perform(get("/umpire"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].umpireId", is((1))))
                .andExpect(jsonPath("$[0].passnumber", is(123)))
                .andExpect(jsonPath("$[0].name", is("Test Umpire Test Umpire")))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
