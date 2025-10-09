package com.dat.backend_version_2.repository.training;

import com.dat.backend_version_2.domain.training.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CoachRepository extends JpaRepository<Coach, UUID> {
    Optional<Coach> findByIdAccount(String idAccount);
}
