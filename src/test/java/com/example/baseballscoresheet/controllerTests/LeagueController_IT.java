package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.AssociationEntity;
import com.example.baseballscoresheet.model.entities.LeagueEntity;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO ich

public class LeagueController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/league"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void findAllLeagues() throws Exception {

        var association = new AssociationEntity();
        association.setId(1L);
        association.setName("Mock-Association");
        this.associationRepository.save(association);

        var league = new LeagueEntity();
        league.setId(1L);
        league.setName("Mock-League");
        league.setAssociation(association);
        this.leagueRepository.save(league);

        this.mockMvc.perform(get("/league"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is("1")))
                .andExpect(jsonPath("$.[0].id", is("1")))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
