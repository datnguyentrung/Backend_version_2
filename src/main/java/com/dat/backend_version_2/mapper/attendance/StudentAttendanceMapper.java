package com.dat.backend_version_2.mapper.attendance;

import com.dat.backend_version_2.domain.attendance.StudentAttendance;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import com.dat.backend_version_2.dto.attendance.StudentAttendanceDTO;
import com.dat.backend_version_2.mapper.training.StudentMapper;

import java.time.LocalDateTime;

public class StudentAttendanceMapper {
    public static AttendanceDTO.StudentAttendanceKey studentAttendanceToAttendanceKey(StudentAttendance studentAttendance) {
        if (studentAttendance == null) {
            return null;
        }
        AttendanceDTO.StudentAttendanceKey attendanceKey = new AttendanceDTO.StudentAttendanceKey();
        attendanceKey.setIdUser(studentAttendance.getStudent().getIdUser());
        attendanceKey.setIdClassSession(studentAttendance.getClassSession().getIdClassSession());
        attendanceKey.setAttendanceDate(studentAttendance.getAttendanceDate());
        return attendanceKey;
    }

    public static AttendanceDTO.AttendanceInfo studentAttendanceToAttendanceInfo(StudentAttendance studentAttendance) {
        if (studentAttendance == null) {
            return null;
        }
        AttendanceDTO.AttendanceInfo attendanceInfo = new AttendanceDTO.AttendanceInfo();
        attendanceInfo.setAttendanceDate(studentAttendance.getAttendanceDate());
        attendanceInfo.setAttendanceStatus(studentAttendance.getAttendanceStatus());
        attendanceInfo.setEvaluationStatus(studentAttendance.getEvaluationStatus());
        attendanceInfo.setNotes(studentAttendance.getNotes());
        return attendanceInfo;
    }

    private static void fillBaseAttendance(StudentAttendance source, StudentAttendanceDTO.CreateStudentAttendance target) {
        target.setAttendanceKey(studentAttendanceToAttendanceKey(source));
        target.setAttendanceInfo(studentAttendanceToAttendanceInfo(source));
    }

    public static StudentAttendanceDTO.CreateStudentAttendance studentAttendanceToCreateAttendance(StudentAttendance s) {
        if (s == null) return null;
        var dto = new StudentAttendanceDTO.CreateStudentAttendance();
        fillBaseAttendance(s, dto);
        return dto;
    }

    public static StudentAttendanceDTO.StudentAttendanceDetail studentAttendanceToStudentAttendanceDetail(StudentAttendance s) {
        if (s == null) return null;
        var dto = new StudentAttendanceDTO.StudentAttendanceDetail();
        fillBaseAttendance(s, dto);
        dto.setPersonalAcademicInfo(StudentMapper.studentToPersonalAcademicInfo(s.getStudent()));
        return dto;
    }

    public static StudentAttendance attendanceInfoToStudentAttendance(AttendanceDTO.AttendanceInfo attendanceInfo) {
        if (attendanceInfo == null) {
            return null;
        }
        StudentAttendance studentAttendance = new StudentAttendance();
        studentAttendance.setAttendanceDate(attendanceInfo.getAttendanceDate());
        studentAttendance.setAttendanceTime(LocalDateTime.now());
        studentAttendance.setAttendanceStatus(attendanceInfo.getAttendanceStatus());
        studentAttendance.setEvaluationStatus(attendanceInfo.getEvaluationStatus());
        studentAttendance.setNotes(attendanceInfo.getNotes());
        return studentAttendance;
    }
}
