package com.dat.backend_version_2.repository.authentication;

import com.dat.backend_version_2.domain.authentication.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
    Users findFirstByIdAccountStartingWithOrderByIdAccountDesc(String find);

    Optional<Object> findByIdAccount(String idAccount);
}
