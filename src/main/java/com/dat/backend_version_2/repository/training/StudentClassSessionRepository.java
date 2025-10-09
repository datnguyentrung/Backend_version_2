package com.dat.backend_version_2.repository.training;

import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.domain.training.StudentClassSession;
import com.dat.backend_version_2.dto.training.StudentClassSession.StudentClassSessionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StudentClassSessionRepository extends JpaRepository<StudentClassSession, StudentClassSessionId> {
    @Query("""
                SELECT scs.student
                FROM StudentClassSession scs
                WHERE scs.idClassSession = :idClassSession
            """)
    List<Student> findStudentsByClassSession(@Param("idClassSession") String idClassSession);

    Boolean existsByIdClassSessionAndIdUser(String idClassSession, UUID idUser);
}
