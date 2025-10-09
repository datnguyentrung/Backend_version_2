package com.dat.backend_version_2.controller.training;

import com.dat.backend_version_2.dto.training.ClassSession.ClassSessionRes;
import com.dat.backend_version_2.service.training.ClassSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class-sessions")
@RequiredArgsConstructor
public class ClassSessionController {
    private final ClassSessionService classSessionService;

    @GetMapping
    public ResponseEntity<List<ClassSessionRes>> getAllClassSessions() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classSessionService.getAllClassSessions());
    }
}
