package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.dto.training.Student.StudentRes;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface StudentRedis {
    void clear();

    List<StudentRes.PersonalAcademicInfo> getAllStudents() throws JsonProcessingException;

    List<StudentRes.PersonalAcademicInfo> getStudentsByIdClassSession(
            String idClassSession
    ) throws JsonProcessingException;

    void saveAllStudents(List<StudentRes.PersonalAcademicInfo> students
    ) throws JsonProcessingException;

    void saveStudentsByIdClassSession(
            String idClassSession,
            List<StudentRes.PersonalAcademicInfo> students
    ) throws JsonProcessingException;
}
