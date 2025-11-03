package com.dat.backend_version_2.service.training;

import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.dto.training.ClassSession.ClassSessionRes;
import com.dat.backend_version_2.mapper.training.ClassSessionMapper;
import com.dat.backend_version_2.redis.training.ClassSessionRedisImpl;
import com.dat.backend_version_2.repository.training.ClassSessionRepository;
import com.dat.backend_version_2.util.error.IdInvalidException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassSessionService {
    private final ClassSessionRepository classSessionRepository;
    private final ClassSessionRedisImpl classSessionRedis;

    public void validateClassSessionExists(String idClassSession) throws IdInvalidException {
        if (!classSessionRepository.existsById(idClassSession)) {
            throw new IdInvalidException("ClassSession not found by id: " + idClassSession);
        }
    }

    public void validateActiveClassSession(String idClassSession) throws IdInvalidException {
        if (!classSessionRepository.existsByIdClassSessionAndIsActiveTrue(idClassSession)) {
            throw new IdInvalidException("Active ClassSession not found by id: " + idClassSession);
        }
    }

    public ClassSession getClassSessionById(String sessionId) throws IdInvalidException, JsonProcessingException {
        ClassSession classSession = classSessionRedis.getClassSessionById(sessionId);
        if (classSession == null) {
            classSession = classSessionRepository.findById(sessionId)
                    .orElseThrow(() -> new IdInvalidException("ClassSession not found by id: " + sessionId));
            classSessionRedis.saveClassSessionById(sessionId, classSession);
        }
        return classSession;
    }

    public List<ClassSessionRes> getAllClassSessions() {
        return classSessionRepository.findAllWithBranch().stream()
                .map(ClassSessionMapper::classSessionToClassSessionRes)
                .toList();
    }
}
