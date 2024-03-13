package com.example.baseballscoresheet.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProtectedController {

    @GetMapping("/protected")
    public Map<String, String> protectedEndpoint() {
        HashMap<String, String> map = new HashMap<>();
        map.put("text", "hello from protected backend GET resource");
        return map;
    }
}
