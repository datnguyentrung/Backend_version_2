package com.dat.backend_version_2.dto.training.Student;

import com.dat.backend_version_2.dto.authentication.UserRes;
import com.dat.backend_version_2.enums.training.Student.Member;
import com.dat.backend_version_2.enums.training.BeltLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentReq {
    // Gom tất cả lại
    private UserRes.UserInfo user;
    private StudentInfo studentInfo; // 👈 Gom tất cả lại

    @Data
    public static class StudentInfo {
        private PersonalInfo personal;
        private ContactInfo contact;
        private AcademicInfo academic;
        private EnrollmentInfo enrollment;
    }

    @Data
    public static class PersonalInfo {
        @NotBlank(message = "Họ tên không được để trống")
        private String name;
        private String idUser;
        private String idNational;
        private LocalDate birthDate;
    }

    @Data
    public static class ContactInfo {
        private String phone;
        private Member member;
    }

    @Data
    public static class AcademicInfo {
        private Integer idBranch; // chỉ truyền id
        private BeltLevel beltLevel;
        private List<String> classSession;
        private Boolean isActive;
    }

    @Data
    public static class EnrollmentInfo {
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
