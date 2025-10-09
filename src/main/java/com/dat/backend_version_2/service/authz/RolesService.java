package com.dat.backend_version_2.service.authz;

import com.dat.backend_version_2.domain.authz.Roles;
import com.dat.backend_version_2.repository.authentication.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    public Roles getRoleById(String roleId){
        return rolesRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role with id " + roleId + " not found"));
    }

    public Roles createRole(Roles role){
        return rolesRepository.save(role);
    }
}
