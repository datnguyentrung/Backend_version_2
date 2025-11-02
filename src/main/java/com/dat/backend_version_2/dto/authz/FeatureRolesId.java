package com.dat.backend_version_2.dto.authz;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode
@NoArgsConstructor    // Hibernate/JPA cần constructor không tham số
@AllArgsConstructor   // Giúp bạn dễ tạo object này
public class FeatureRolesId {
    private UUID idFeature;
    private String idRole;
}
