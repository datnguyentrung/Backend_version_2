package com.dat.backend_version_2.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesRes {
    private String idRole;
    private String roleName;
}
