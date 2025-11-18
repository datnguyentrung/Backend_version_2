package com.dat.backend_version_2.dto.attendance;

import com.dat.backend_version_2.dto.training.Student.StudentRes;
import com.dat.backend_version_2.enums.attendance.AttendanceStatus;
import com.dat.backend_version_2.enums.attendance.EvaluationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class StudentAttendanceDTO {
    @Data
    public static class StudentMarkAttendance {
        private AttendanceDTO.StudentAttendanceAccountKey attendanceAccountKey;
        private AttendanceStatus attendanceStatus;
    }

    @Data
    public static class StudentMarkEvaluation {
        private AttendanceDTO.StudentAttendanceAccountKey attendanceAccountKey;
        private EvaluationStatus evaluationStatus;
        private String notes;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class StudentAttendanceDetail extends AttendanceDTO.AttendanceInfo {
        private StudentRes.PersonalAcademicInfo personalAcademicInfo;
        private LocalDate attendanceDate;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentAttendanceClassSession {
        private String idClassSession;
        private LocalDate attendanceDate;
    }
}
