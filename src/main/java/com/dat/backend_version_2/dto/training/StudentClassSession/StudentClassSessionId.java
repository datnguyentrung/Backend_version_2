package com.dat.backend_version_2.dto.training.StudentClassSession;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode
@NoArgsConstructor    // Hibernate/JPA cần constructor không tham số
@AllArgsConstructor   // Giúp bạn dễ tạo object này
public class StudentClassSessionId {
    private UUID idUser;
    private String idClassSession;
}