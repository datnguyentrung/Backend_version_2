package com.dat.backend_version_2.mapper.authentication;

import com.dat.backend_version_2.domain.authz.Roles;
import com.dat.backend_version_2.dto.authentication.RolesRes;

public class RolesMapper {
    public static RolesRes toRolesRes(Roles roles) {
        if (roles == null){
            return null;
        }
        RolesRes rolesRes = new RolesRes();
        rolesRes.setIdRole(roles.getIdRole());
        rolesRes.setRoleName(roles.getRoleName());

        return rolesRes;
    }
}
