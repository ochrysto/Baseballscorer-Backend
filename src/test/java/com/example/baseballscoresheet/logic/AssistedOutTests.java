package com.example.baseballscoresheet.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

public class AssistedOutTests extends AbstractLogicIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    /** Batter and runner on the 3rd base
     * Runner on the ...
     *
     * @throws Exception - ...
     */
    @Test
    @Transactional
    @WithMockUser(roles = "user")
    public void testNoLinkedActions() throws Exception {

    }
}
