package com.dat.backend_version_2.dto.attendance;

import com.dat.backend_version_2.enums.attendance.AttendanceStatus;
import com.dat.backend_version_2.enums.attendance.EvaluationStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceDTO {
    private String idAttendance;

    private String idStudent;
    private String studentName;
    private String idClassSession;
    private AttendanceInfo attendanceInfo;

    @Data
    public static class AttendanceInfo{
        private LocalDate attendanceDate;
        private AttendanceStatus attendanceStatus;
        private EvaluationStatus evaluationStatus;
        private String notes;
    }

    @Data
    public static class MarkAttendance{
        private String idAttendance;
        private AttendanceStatus attendanceStatus;
    }

    @Data
    public static class MarkEvaluation{
        private String idAttendance;
        private EvaluationStatus evaluationStatus;
    }
}
