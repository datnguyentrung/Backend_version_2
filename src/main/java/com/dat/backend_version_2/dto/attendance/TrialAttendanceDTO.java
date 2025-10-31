package com.dat.backend_version_2.dto.attendance;

import com.dat.backend_version_2.dto.registration.RegistrationDTO;
import com.dat.backend_version_2.enums.attendance.AttendanceStatus;
import com.dat.backend_version_2.enums.attendance.EvaluationStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Data
    public static class CreateTrialAttendance{
        private AttendanceDTO.TrialAttendanceKey attendanceKey;
        private AttendanceDTO.AttendanceInfo attendanceInfo;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class TrialAttendanceDetail extends CreateTrialAttendance{
        private RegistrationDTO registrationDTO;
    }
}
