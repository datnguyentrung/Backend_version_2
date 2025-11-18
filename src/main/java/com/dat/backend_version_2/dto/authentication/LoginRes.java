package com.dat.backend_version_2.dto.authentication;

import com.dat.backend_version_2.enums.authentication.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class LoginRes {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private String idDevice;
    private UserLogin user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLogin {
        private String idAccount;
        private UserStatus status;
        private String role;
        private String startDate;

        @Override
        public String toString() {
            return "UserLogin{id= " + idAccount + ", status= " + status + "}";
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserGetAccount {
        private UserLogin userLogin;
    }
}
