package com.dat.backend_version_2.dto.authentication;

import com.dat.backend_version_2.domain.authz.Roles;
import com.dat.backend_version_2.enums.training.BeltLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {
    private UserInfo userInfo;
    private UserProfile userProfile;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private UUID idUser;
        private String idAccount;
        private String email;
        private String idRole;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProfile {
        private LocalDate birthDate;
        private Boolean isActive;
        private String name;
        private String phone;
        private BeltLevel beltLevel;
    }
}
