package com.dat.backend_version_2.dto.attendance;

import com.dat.backend_version_2.dto.registration.RegistrationDTO;
import com.dat.backend_version_2.enums.attendance.AttendanceStatus;
import com.dat.backend_version_2.enums.attendance.EvaluationStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
public class TrialAttendanceDTO {
    @Data
    public static class TrialMarkAttendance{
        private AttendanceDTO.TrialAttendanceKey attendanceKey;
        private AttendanceStatus attendanceStatus;
    }

    @Data
    public static class TrialMarkEvaluation{
        private AttendanceDTO.TrialAttendanceKey attendanceKey;
        private EvaluationStatus evaluationStatus;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class TrialAttendanceDetail extends AttendanceDTO.AttendanceInfo{
        private RegistrationDTO registrationDTO;
        private LocalDate attendanceDate;
    }
}
