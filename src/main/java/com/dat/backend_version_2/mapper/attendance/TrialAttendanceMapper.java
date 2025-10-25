package com.dat.backend_version_2.mapper.attendance;

import com.dat.backend_version_2.domain.attendance.TrialAttendance;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TrialAttendanceMapper {
    public static TrialAttendance attendanceInfoToTrialAttendance(
            AttendanceDTO.AttendanceInfo attendanceInfo) {
        if (attendanceInfo == null) {
            return null;
        }
        TrialAttendance attendance = new TrialAttendance();
        attendance.setAttendanceStatus(attendanceInfo.getAttendanceStatus());
        attendance.setAttendanceTime(LocalDateTime.now());

        attendance.setEvaluationStatus(attendanceInfo.getEvaluationStatus());
        attendance.setNotes(attendanceInfo.getNotes());

        return attendance;
    }

    public static AttendanceDTO trialAttendanceToAttendanceDTO(TrialAttendance trialAttendance) {
        if (trialAttendance == null) {
            return null;
        }
        AttendanceDTO attendance = new AttendanceDTO();
        attendance.setIdStudent(String.valueOf(trialAttendance.getRegistration().getIdRegistration()));
        attendance.setStudentName(trialAttendance.getRegistration().getName());
        attendance.setIdClassSession(trialAttendance.getClassSession().getIdClassSession());
        attendance.setAttendanceInfo(trialAttendanceToAttendanceInfo(trialAttendance));

        return attendance;
    }

    public static AttendanceDTO.AttendanceInfo trialAttendanceToAttendanceInfo(TrialAttendance trialAttendance) {
        if (trialAttendance == null) {
            return null;
        }
        AttendanceDTO.AttendanceInfo attendanceInfo = new AttendanceDTO.AttendanceInfo();
        attendanceInfo.setAttendanceDate(trialAttendance.getAttendanceDate());
        attendanceInfo.setAttendanceStatus(trialAttendance.getAttendanceStatus());
        attendanceInfo.setEvaluationStatus(trialAttendance.getEvaluationStatus());
        attendanceInfo.setNotes(trialAttendance.getNotes());

        return attendanceInfo;
    }
}
