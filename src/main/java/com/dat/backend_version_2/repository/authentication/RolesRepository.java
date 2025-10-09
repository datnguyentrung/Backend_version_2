package com.dat.backend_version_2.repository.authentication;

import com.dat.backend_version_2.domain.authz.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, String> {
}
