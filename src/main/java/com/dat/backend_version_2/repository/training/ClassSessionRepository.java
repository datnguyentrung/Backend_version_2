package com.dat.backend_version_2.repository.training;

import com.dat.backend_version_2.domain.training.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassSessionRepository extends JpaRepository<ClassSession, String> {
    @Query("""
            SELECT DISTINCT cs FROM ClassSession cs
                        JOIN FETCH cs.branch b
            """)
    List<ClassSession> findAllWithBranch();
}
