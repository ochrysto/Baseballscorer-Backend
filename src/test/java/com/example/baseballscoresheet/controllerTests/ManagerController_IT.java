package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.ManagerEntity;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/manager"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void findAllAvailableManagers() throws Exception {
        var manager = new ManagerEntity();
        manager.setId(1L);
        manager.setFirstName("Test Manager");
        manager.setLastName("Test Manager");
        manager.setEmail("manager@test.com");

        this.managerRepository.save(manager);

        this.mockMvc.perform(get("/manager"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Test Manager")))
                .andExpect(jsonPath("$[0].lastName", is("Test Manager")))
                .andExpect(jsonPath("$[0].email", is("manager@test.com")))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}
