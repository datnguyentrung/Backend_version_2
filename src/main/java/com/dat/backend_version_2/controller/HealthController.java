package com.dat.backend_version_2.controller;

import com.dat.backend_version_2.util.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<?> root(@AuthenticationPrincipal Jwt jwt) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", "UP");
//        response.put("message", "Backend Version 2 API is running");
//        response.put("timestamp", System.currentTimeMillis());
//        return ResponseEntity.ok(response);
        System.out.println("hello");
        return ResponseEntity.ok(SecurityUtil.getCurrentUser(jwt));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        return ResponseEntity.ok(status);
    }
}
