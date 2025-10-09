package com.dat.backend_version_2.repository.authentication;

import com.dat.backend_version_2.domain.authentication.UserTokens;
import com.dat.backend_version_2.domain.authentication.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTokensRepository extends JpaRepository<UserTokens, UUID> {
    Optional<UserTokens> findByUser(Users user);

    Optional<UserTokens> findByUserAndIdDevice(Users user, String idDevice);
}
