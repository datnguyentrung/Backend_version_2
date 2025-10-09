package com.dat.backend_version_2.repository.registration;

import com.dat.backend_version_2.domain.registration.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, UUID> {
    Optional<Registration> findByNameAndPhone(String name, String phone);

    @Query("""
            SELECT DISTINCT r
            FROM Registration r
            LEFT JOIN FETCH r.branch
            """)
    List<Registration> findAllWithBranch();
}
