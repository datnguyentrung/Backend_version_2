package com.dat.backend_version_2.mapper.attendance;

import com.dat.backend_version_2.domain.attendance.StudentAttendance;
import com.dat.backend_version_2.domain.attendance.TrialAttendance;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import com.dat.backend_version_2.dto.attendance.StudentAttendanceDTO;
import com.dat.backend_version_2.dto.attendance.TrialAttendanceDTO;
import com.dat.backend_version_2.mapper.registration.RegistrationMapper;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TrialAttendanceMapper {
    public static TrialAttendance attendanceInfoToTrialAttendance(
            AttendanceDTO.AttendanceInfo attendanceInfo) {
        if (attendanceInfo == null) {
            return null;
        }
        TrialAttendance attendance = new TrialAttendance();
        attendance.setAttendanceStatus(attendanceInfo.getAttendance().getAttendanceStatus());
        attendance.setAttendanceTime(LocalTime.now());

        attendance.setEvaluationStatus(attendanceInfo.getEvaluation().getEvaluationStatus());
        attendance.setNotes(attendanceInfo.getNotes());

        return attendance;
    }

    public static AttendanceDTO.AttendanceInfo trialAttendanceToAttendanceInfo(TrialAttendance trialAttendance) {
        if (trialAttendance == null) {
            return null;
        }
        AttendanceDTO.AttendanceInfo attendanceInfo = new AttendanceDTO.AttendanceInfo();
        attendanceInfo.setAttendance(trialAttendanceToAttendanceDetail(trialAttendance));
        attendanceInfo.setEvaluation(trialAttendanceToEvaluationDetail(trialAttendance));
        attendanceInfo.setNotes(trialAttendance.getNotes());

        return attendanceInfo;
    }

    public static AttendanceDTO.AttendanceDetail trialAttendanceToAttendanceDetail(TrialAttendance trialAttendance) {
        if (trialAttendance == null) {
            return null;
        }
        AttendanceDTO.AttendanceDetail attendanceDetail = new AttendanceDTO.AttendanceDetail();
        attendanceDetail.setAttendanceTime(
                trialAttendance.getAttendanceTime() != null ?
                        trialAttendance.getAttendanceTime() :
                        null
        );
        attendanceDetail.setAttendanceStatus(trialAttendance.getAttendanceStatus());
        return attendanceDetail;
    }

    public static AttendanceDTO.EvaluationDetail trialAttendanceToEvaluationDetail(TrialAttendance trialAttendance) {
        if (trialAttendance == null) {
            return null;
        }
        AttendanceDTO.EvaluationDetail evaluationDetail = new AttendanceDTO.EvaluationDetail();
        evaluationDetail.setEvaluationStatus(trialAttendance.getEvaluationStatus());
        return evaluationDetail;
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


    public static TrialAttendanceDTO.TrialAttendanceDetail trialAttendanceToTrialAttendanceDetail(TrialAttendance s) {
        if (s == null) return null;
        AttendanceDTO.AttendanceInfo attendanceInfo = trialAttendanceToAttendanceInfo(s);
        var dto = new TrialAttendanceDTO.TrialAttendanceDetail();
        BeanUtils.copyProperties(attendanceInfo, dto);
        dto.setRegistrationDTO(RegistrationMapper.registrationToRegistrationDTO(s.getRegistration()));
        dto.setAttendanceDate(s.getAttendanceDate());
        dto.setIdClassSession(s.getIdClassSession());
        dto.setIdAccount(s.getRegistration().getIdRegistration().toString());
        return dto;
    }
}
