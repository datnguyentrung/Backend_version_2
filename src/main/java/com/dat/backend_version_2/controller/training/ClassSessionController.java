package com.dat.backend_version_2.controller.training;

import com.dat.backend_version_2.dto.training.ClassSession.ClassSessionRes;
import com.dat.backend_version_2.redis.training.ClassSessionRedisImpl;
import com.dat.backend_version_2.service.training.ClassSessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
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
    private final ClassSessionRedisImpl classSessionRedis;

    @GetMapping
    public ResponseEntity<List<ClassSessionRes>> getAllClassSessions() throws JsonProcessingException {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(classSessionService.getAllClassSessions());
        List<ClassSessionRes> classSessions = classSessionRedis.getAllClassSessions();
        if (classSessions == null){
            classSessions = classSessionService.getAllClassSessions();
            classSessionRedis.saveAllClassSessions(classSessions);
        }
        return ResponseEntity.ok(classSessions);
    }
}
