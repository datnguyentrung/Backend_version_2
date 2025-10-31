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
    @Data
    public static class AttendanceInfo{
        private String idAccount;
        private LocalDate attendanceDate;
        private AttendanceStatus attendanceStatus;
        private EvaluationStatus evaluationStatus;
        private String notes;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CoachAttendanceKey implements Serializable {
        private UUID idUser;
        private String idClassSession;
        private LocalDate attendanceDate;
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
