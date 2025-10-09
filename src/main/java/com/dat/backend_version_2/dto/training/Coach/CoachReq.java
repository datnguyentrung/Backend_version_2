package com.dat.backend_version_2.dto.training.Coach;

import com.dat.backend_version_2.dto.authentication.LoginReq;
import com.dat.backend_version_2.enums.training.BeltLevel;
import com.dat.backend_version_2.enums.training.Coach.Position;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CoachReq {
    // Gom tất cả lại
    private LoginReq.UserBase user;
    private CoachInfo coachInfo;

    @Data
    public static class CoachInfo {
        private PersonalInfo personal;
        private ContactInfo contact;
        private JobInfo job;
    }

    @Data
    public static class PersonalInfo {
        private String name;
        private LocalDate birthDate;
    }

    @Data
    public static class ContactInfo {
        private String phone;
    }

    @Data
    public static class JobInfo {
        private Position position;
        private BeltLevel beltLevel;
        private List<String> classSession;
        private Boolean isActive;
    }
}