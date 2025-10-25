package com.dat.backend_version_2.redis.attendance;

import com.dat.backend_version_2.dto.attendance.CoachAttendanceRes;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface CoachAttendanceRedis {
    void clear();

    void deleteByKey(String key);

    List<CoachAttendanceRes> getCoachAttendanceByIdCoachAndYearAndMonth(
            String idCoach,
            Integer year,
            Integer month
    ) throws JsonProcessingException;

    void saveCoachAttendanceByIdCoachAndYearAndMonth(
            String idCoach,
            Integer year,
            Integer month,
            List<CoachAttendanceRes> coachAttendanceResList
    ) throws JsonProcessingException;
}
