package com.example.baseballscoresheet.logic;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthTests extends TestConfiguration {
    @Test
    @Transactional
    @WithMockUser(roles = "user")
    void protectedResourceTest() throws Exception {
        this.mockMvc.perform(get("/protected")).andExpect(status().is2xxSuccessful());
    }

    @Test
    @Transactional
    void protectedResourceWithoutAuthTest() throws Exception {
        this.mockMvc.perform(get("/protected")).andExpect(status().is4xxClientError());
    }
}
