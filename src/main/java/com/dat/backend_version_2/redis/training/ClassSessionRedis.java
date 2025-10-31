package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.dto.training.ClassSession.ClassSessionRes;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ClassSessionRedis {
    void clear();

    List<ClassSessionRes> getAllClassSessions() throws JsonProcessingException;

    ClassSession getClassSessionById(String idClassSession) throws JsonProcessingException;

    void saveClassSessionById(String idClassSession, ClassSession classSession
    ) throws JsonProcessingException;

    void saveAllClassSessions(List<ClassSessionRes> classSessions
    ) throws JsonProcessingException;
}
