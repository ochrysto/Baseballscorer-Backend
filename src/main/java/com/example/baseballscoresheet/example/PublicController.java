package com.example.baseballscoresheet.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PublicController {

    @GetMapping("/public")
    public Map<String, String> publicEndpoint() {
        HashMap<String, String> map = new HashMap<>();
        map.put("text", "hello from public backend GET resource");
        return map;
    }
}
