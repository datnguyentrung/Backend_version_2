package com.dat.backend_version_2.repository.attendance;

import com.dat.backend_version_2.domain.attendance.StudentAttendance;
import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, UUID> {
    @Query("""
                SELECT DISTINCT sa
                FROM StudentAttendance sa
                JOIN FETCH sa.student
                JOIN FETCH sa.classSession cs
                WHERE cs.idClassSession = :idClassSession
                  AND sa.attendanceDate = :attendanceDate
            """)
    List<StudentAttendance> findByIdClassSessionAndAttendanceDate(
            @Param("idClassSession") String idClassSession,
            @Param("attendanceDate") LocalDate attendanceDate
    );

    Boolean existsByStudentAndClassSessionAndAttendanceDate(Student student, ClassSession classSession, LocalDate attendanceDate);
}
