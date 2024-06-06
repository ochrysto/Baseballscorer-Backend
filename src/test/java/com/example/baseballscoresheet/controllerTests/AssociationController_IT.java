package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.AssociationEntity;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AssociationController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/association"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void findAllAssociations() throws Exception {

        var association = new AssociationEntity();
        association.setId(1L);
        association.setName("Test Association");

        this.associationRepository.save(association);

        this.mockMvc.perform(get("/association"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id",is((1))))
                .andExpect(jsonPath("$.[0].name", is("Test Association")))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
