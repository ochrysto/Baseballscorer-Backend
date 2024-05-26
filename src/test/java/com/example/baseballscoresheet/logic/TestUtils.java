package com.example.baseballscoresheet.logic;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public void compareDicts(Map<String, Object> expected, Map<String, Object> actual) {
        for (String key : expected.keySet()) {
            assertTrue(actual.containsKey(key), "Key '" + key + "' not found in response data");
            Object expectedValue = expected.get(key);
            Object actualValue = actual.get(key);
            if (expectedValue instanceof Map) {
                compareDicts((Map<String, Object>) expectedValue, (Map<String, Object>) actualValue);
            } else {
                assertEquals(expectedValue, actualValue, "Value mismatch for key '" + key + "': expected '" + expectedValue + "', got '" + actualValue + "'");
            }
        }
    }

    public void checkGameState(Long gameId, Map<String, Object> expectedData) throws Exception {
        MvcResult result = mockMvc.perform(get("/game/" + gameId + "/state")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> actualData = objectMapper.readValue(responseBody, Map.class);
        compareDicts(expectedData, actualData);
    }

    public void checkAvailableActions(Long gameId, Map<String, Object> expectedActions) throws Exception {
        MvcResult result = mockMvc.perform(get("/game/" + gameId + "/action")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> actualActions = objectMapper.readValue(responseBody, Map.class);
        compareDicts(expectedActions, actualActions);
    }

    public void createAction(Long gameId, int base, ActionEntity.Type action, Integer distance, List<Map<String, Object>> responsible) throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("base", base);
        requestBody.put("type", action);
        if (distance != null) {
            requestBody.put("distance", distance);
        }
        if (responsible != null) {
            requestBody.put("responsible", responsible);
        }

        String requestJson = objectMapper.writeValueAsString(requestBody);

        MvcResult result = mockMvc.perform(post("/game/" + gameId + "/action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

        assertNotNull(responseMap, "Response JSON must have action data");
        assertEquals(1, responseMap.size(), "Response JSON must have 1 field");
        assertTrue(responseMap.containsKey("msg"), "Response JSON must have `msg` field");
    }
}
