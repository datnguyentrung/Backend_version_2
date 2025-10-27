package com.dat.backend_version_2.mapper.attendance;

import com.dat.backend_version_2.domain.attendance.StudentAttendance;
import com.dat.backend_version_2.domain.attendance.TrialAttendance;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import com.dat.backend_version_2.dto.attendance.StudentAttendanceDTO;
import com.dat.backend_version_2.dto.attendance.TrialAttendanceDTO;
import com.dat.backend_version_2.mapper.registration.RegistrationMapper;

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

    public static AttendanceDTO.TrialAttendanceKey trialAttendanceToAttendanceKey(TrialAttendance trialAttendance) {
        if (trialAttendance == null) {
            return null;
        }
        AttendanceDTO.TrialAttendanceKey attendanceKey = new AttendanceDTO.TrialAttendanceKey();
        attendanceKey.setIdRegistration(trialAttendance.getRegistration().getIdRegistration());
        attendanceKey.setIdClassSession(trialAttendance.getClassSession().getIdClassSession());
        attendanceKey.setAttendanceDate(trialAttendance.getAttendanceDate());
        return attendanceKey;
    }

    private static void fillBaseAttendance(TrialAttendance source, TrialAttendanceDTO.CreateTrialAttendance target) {
        target.setAttendanceKey(trialAttendanceToAttendanceKey(source));
        target.setAttendanceInfo(trialAttendanceToAttendanceInfo(source));
    }

    public static TrialAttendanceDTO.CreateTrialAttendance trialAttendanceToCreateAttendance(TrialAttendance s) {
        if (s == null) return null;
        var dto = new TrialAttendanceDTO.CreateTrialAttendance();
        fillBaseAttendance(s, dto);
        return dto;
    }

    public static TrialAttendanceDTO.TrialAttendanceDetail trialAttendanceToTrialAttendanceDetail(TrialAttendance s) {
        if (s == null) return null;
        var dto = new TrialAttendanceDTO.TrialAttendanceDetail();
        fillBaseAttendance(s, dto);
        dto.setRegistrationDTO(RegistrationMapper.registrationToRegistrationDTO(s.getRegistration()));
        return dto;
    }
}
