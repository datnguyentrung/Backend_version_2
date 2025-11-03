package com.dat.backend_version_2.mapper.attendance;

import com.dat.backend_version_2.domain.attendance.CoachAttendance;
import com.dat.backend_version_2.dto.attendance.CoachAttendanceRes;
import com.dat.backend_version_2.mapper.training.ClassSessionMapper;


public class CoachAttendanceMapper {
    public static CoachAttendanceRes coachAttendanceToCoachAttendanceRes(CoachAttendance coachAttendance) {
        if (coachAttendance == null) {
            return null;
        }
        CoachAttendanceRes res = new CoachAttendanceRes();

        // Safely access coach name without triggering lazy loading of other properties
        if (coachAttendance.getCoach() != null) {
            res.setName(coachAttendance.getCoach().getName());
        }

        res.setCreatedAt(coachAttendance.getCreatedAt());
        res.setClassSession(ClassSessionMapper.classSessionToClassSessionRes(coachAttendance.getClassSession()));
        return res;
    }
}
