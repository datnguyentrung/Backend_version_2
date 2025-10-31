package com.dat.backend_version_2.dto.training.Student;

import com.dat.backend_version_2.dto.authentication.UserRes;
import com.dat.backend_version_2.enums.training.Student.Member;
import com.dat.backend_version_2.enums.training.BeltLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRes {
    private UserRes.UserInfo user;
    private StudentInfo studentInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentInfo {
        private PersonalInfo personal;
        private ContactInfo contact;
        private AcademicInfo academic;
        private EnrollmentInfo enrollment;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PersonalInfo {
        private String name;
        private String idAccount;
        private String idNational;
        private LocalDate birthDate;
        private Boolean isActive;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContactInfo {
        private String phone;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AcademicInfo {
        private Integer idBranch;
        private BeltLevel beltLevel;
        private List<String> classSessions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnrollmentInfo {
        private LocalDate startDate;
        private LocalDate endDate;
        private Member member;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PersonalAcademicInfo {
        private PersonalInfo personalInfo;
        private AcademicInfo academicInfo;
    }
}
