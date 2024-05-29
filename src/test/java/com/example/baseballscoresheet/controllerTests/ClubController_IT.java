package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.AssociationEntity;
import com.example.baseballscoresheet.model.entities.ClubEntity;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClubController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/club"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void findAllClubs() throws Exception {
        var association = new AssociationEntity();
        association.setId(1L);
        association.setName("Test Association");
        this.associationRepository.save(association);

        var club = new ClubEntity();
        club.setId(1L);
        club.setAssociation(association);
        club.setName("Club Nr. 1");
        club.setClubLogo("Club-Logo");
        club.setEmail("club@web.de");
        club.setCity("Club-City");

        this.clubRepository.save(club);

        this.mockMvc.perform(get("/club"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name", is("Club Nr. 1")))
                .andExpect(jsonPath("$.[0].logo", is("Club-Logo")))
                .andExpect(jsonPath("$.[0].email", is("club@web.de")))
                .andExpect(jsonPath("$.[0].city", is("Club-City")))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
