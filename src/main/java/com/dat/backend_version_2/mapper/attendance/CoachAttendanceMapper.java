package com.dat.backend_version_2.mapper.attendance;

import com.dat.backend_version_2.domain.attendance.CoachAttendance;
import com.dat.backend_version_2.dto.attendance.CoachAttendanceRes;

public class CoachAttendanceMapper {
    public static CoachAttendanceRes coachAttendanceToCoachAttendanceRes(CoachAttendance coachAttendance) {
        if (coachAttendance == null) {
            return null;
        }
        CoachAttendanceRes res = new CoachAttendanceRes();
        res.setName(coachAttendance.getCoach().getName());
        res.setAttendanceDate(coachAttendance.getAttendanceDate());
        res.setClassSession(coachAttendance.getClassSession());
        return res;
    }
}
