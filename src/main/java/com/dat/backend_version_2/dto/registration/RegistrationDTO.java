package com.dat.backend_version_2.dto.registration;

import com.dat.backend_version_2.enums.registration.RegistrationStatus;
import com.dat.backend_version_2.enums.training.BeltLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegistrationDTO {
    private String idRegistration;
    private PersonalInfo personalInfo;
    private RegistrationInfo registrationInfo;

    @Data
    public static class PersonalInfo {
        @NotBlank(message = "Name cannot be blank")
        private String name;

        @PastOrPresent(message = "Birth date cannot be in the future")
        private LocalDate birthDate;

        @NotBlank(message = "Phone number cannot be blank")
        private String phone;
        private String referredBy;
    }

    @Data
    public static class RegistrationInfo {
        private Integer idBranch; // Có thể null
        private List<String> idClassSession; // Lớp học
        private BeltLevel beltLevel;
        private LocalDate registrationDate;
        private RegistrationStatus registrationStatus;
    }
}
