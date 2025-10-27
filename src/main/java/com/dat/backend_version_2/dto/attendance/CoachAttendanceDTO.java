package com.dat.backend_version_2.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CoachAttendanceDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        private Instant createdAt;        // thời gian điểm danh
        private String fileName;         // tên file ảnh minh chứng (ví dụ: "attendance_2025_10_22.jpg")
        private String idClassSession;   // id lớp học mà HLV điểm danh
        private String idAccount;        // id tài khoản HLV điểm danh
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoachAttendanceResponse {
        private String idAttendance;     // UUID dạng string
        private String idCoach;
        private String idClassSession;
        private String imageUrl;
        private LocalDateTime createdAt;
    }
}