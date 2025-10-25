package com.dat.backend_version_2.mapper.attendance;

import com.dat.backend_version_2.domain.attendance.StudentAttendance;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class StudentAttendanceMapper {
    public static AttendanceDTO studentAttendanceToAttendanceDTO(StudentAttendance studentAttendance) {
        if (studentAttendance == null) {
            return null;
        }
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setIdStudent(String.valueOf(studentAttendance.getStudent().getIdAccount()));
        attendanceDTO.setIdClassSession(studentAttendance.getClassSession().getIdClassSession());

        AttendanceDTO.AttendanceInfo attendanceInfo = new AttendanceDTO.AttendanceInfo();
        attendanceInfo.setAttendanceDate(studentAttendance.getAttendanceDate());
        attendanceInfo.setAttendanceStatus(studentAttendance.getAttendanceStatus());
        attendanceInfo.setEvaluationStatus(studentAttendance.getEvaluationStatus());
        attendanceInfo.setNotes(studentAttendance.getNotes());

        attendanceDTO.setAttendanceInfo(attendanceInfo);
        return attendanceDTO;
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
