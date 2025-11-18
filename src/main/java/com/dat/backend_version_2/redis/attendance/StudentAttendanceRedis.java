package com.dat.backend_version_2.redis.attendance;

import com.dat.backend_version_2.dto.attendance.StudentAttendanceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.util.List;

public interface StudentAttendanceRedis {
    void clear();

    void deleteByKey(String key);

    List<StudentAttendanceDTO.StudentAttendanceDetail> getAttendanceByClassSessionAndDate(
            String idClassSession,
            LocalDate attendanceDate
    ) throws JsonProcessingException;

    void saveAttendanceByClassSessionAndDate(
            String idClassSession,
            LocalDate attendanceDate,
            List<StudentAttendanceDTO.StudentAttendanceDetail> attendanceDetailList
    ) throws JsonProcessingException;
}
