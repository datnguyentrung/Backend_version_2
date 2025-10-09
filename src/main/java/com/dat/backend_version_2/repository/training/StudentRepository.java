package com.dat.backend_version_2.repository.training;

import com.dat.backend_version_2.domain.training.Branch;
import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.domain.training.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("""
            SELECT DISTINCT s FROM Student s
                        JOIN FETCH s.branch b
                        LEFT JOIN FETCH s.studentClassSessions scs
                        LEFT JOIN FETCH scs.classSession cs
            """)
    List<Student> findAllWithBranchAndSessions();

    List<Student> findByBranch(Branch branch);

    @Query("""
            SELECT DISTINCT s FROM Student s
                        JOIN FETCH s.studentClassSessions scs
            WHERE scs.classSession = :classSession
            """)
    List<Student> findAllByIdClassSession(@Param("classSession") ClassSession classSession);

    Optional<Student> findByIdAccount(String idAccount);
}
