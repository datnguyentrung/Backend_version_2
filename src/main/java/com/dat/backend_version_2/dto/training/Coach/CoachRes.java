package com.dat.backend_version_2.dto.training.Coach;

import com.dat.backend_version_2.dto.authentication.UserRes;
import com.dat.backend_version_2.enums.training.BeltLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoachRes {
    private UserRes.UserInfo user;   // Thông tin chung từ Users
    private CoachInfo coachInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoachInfo{
        private PersonalInfo personal;   // Thông tin cá nhân
        private ContactInfo contact;     // Thông tin liên hệ
        private ProfessionalInfo professional; // Thông tin chuyên môn
        private AssignmentInfo assignment;     // Thông tin phân công lớp học
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PersonalInfo {
        private String name;
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
    public static class ProfessionalInfo {
        private String position;
        private BeltLevel beltLevel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssignmentInfo {
        private List<String> classSessions; // Danh sách tên hoặc id ClassSession mà coach phụ trách
    }
}