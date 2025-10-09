package com.dat.backend_version_2.service.training;

import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.dto.training.ClassSession.ClassSessionRes;
import com.dat.backend_version_2.mapper.training.ClassSessionMapper;
import com.dat.backend_version_2.repository.training.ClassSessionRepository;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassSessionService {
    private final ClassSessionRepository classSessionRepository;

    public ClassSession getClassSessionById(String sessionId) throws IdInvalidException {
        return classSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IdInvalidException("ClassSession not found by id: " + sessionId));
    }

    public List<ClassSessionRes> getAllClassSessions() {
        return classSessionRepository.findAllWithBranch().stream()
                .map(ClassSessionMapper::classSessionToClassSessionRes)
                .toList();
    }
}
