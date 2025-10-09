package com.dat.backend_version_2.repository.attendance;

import com.dat.backend_version_2.domain.attendance.CoachAttendance;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CoachAttendanceRepository extends JpaRepository<CoachAttendance, UUID>, JpaSpecificationExecutor<CoachAttendance> {
    @EntityGraph(attributePaths = {"coach", "classSession", "classSession.branch"})
    List<CoachAttendance> findAll(Specification<CoachAttendance> spec);
}
