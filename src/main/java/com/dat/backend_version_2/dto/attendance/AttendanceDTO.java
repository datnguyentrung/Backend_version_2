package com.dat.backend_version_2.dto.attendance;

import com.dat.backend_version_2.enums.attendance.AttendanceStatus;
import com.dat.backend_version_2.enums.attendance.EvaluationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class AttendanceDTO {
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentAttendanceKey implements Serializable {
        private UUID idUser;
        private String idClassSession;
        private LocalDate attendanceDate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TrialAttendanceKey implements Serializable {
        private UUID idRegistration;
        private String idClassSession;
        private LocalDate attendanceDate;
    }
}
