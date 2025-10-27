package com.dat.backend_version_2.dto.attendance;

import com.dat.backend_version_2.dto.training.Student.StudentRes;
import com.dat.backend_version_2.enums.attendance.AttendanceStatus;
import com.dat.backend_version_2.enums.attendance.EvaluationStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class StudentAttendanceDTO {
    @Data
    public static class StudentMarkAttendance{
        private AttendanceDTO.StudentAttendanceKey attendanceKey;
        private AttendanceStatus attendanceStatus;
    }

    @Data
    public static class StudentMarkEvaluation{
        private AttendanceDTO.StudentAttendanceKey attendanceKey;
        private EvaluationStatus evaluationStatus;
    }

    @Data
    public static class CreateStudentAttendance{
        private AttendanceDTO.StudentAttendanceKey attendanceKey;
        private AttendanceDTO.AttendanceInfo attendanceInfo;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class StudentAttendanceDetail extends CreateStudentAttendance{
        private StudentRes.PersonalAcademicInfo personalAcademicInfo;
    }
}
