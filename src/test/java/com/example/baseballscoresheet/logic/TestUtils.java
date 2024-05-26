package com.example.baseballscoresheet.logic;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TestUtils {
    final private String HOST = "http://localhost:8080";
    final private RestTemplate restTemplate;

    @Autowired
    public TestUtils(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }


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

    public void checkGameState(Long gameId, Map<String, Object> expectedData) {
        ResponseEntity<Map> response = restTemplate.getForEntity(HOST + "/game/" + gameId + "/state", Map.class);
        Map<String, Object> actualData = response.getBody();
        compareDicts(expectedData, actualData);
    }

    public void checkAvailableActions(Long gameId, Map<String, Object> expectedActions) {
        ResponseEntity<Map> response = restTemplate.getForEntity(HOST + "/game/" + gameId + "/action", Map.class);
        Map<String, Object> actualActions = response.getBody();
        compareDicts(expectedActions, actualActions);
    }

    public void createAction(Long gameId, int base, ActionEntity.Type action, Integer distance, List<Map<String, Object>> responsible) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("base", base);
        requestBody.put("type", action);
        if (distance != null) {
            requestBody.put("distance", distance);
        }
        if (responsible != null) {
            requestBody.put("responsible", responsible);
        }

        ResponseEntity<Map> response = restTemplate.postForEntity(HOST + "/game/" + gameId + "/action", requestBody, Map.class);
        assertEquals(200, response.getStatusCodeValue(), "Something went wrong");
        assertNotNull(response.getBody(), "Response JSON must have action data");
        assertEquals(1, response.getBody().size(), "Response JSON must have 1 field");
        assertTrue(response.getBody().containsKey("msg"), "Response JSON must have `msg` field");
    }
}
