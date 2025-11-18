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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, AttendanceDTO.StudentAttendanceKey> {
    @Query("""
                SELECT DISTINCT sa
                FROM StudentAttendance sa
                JOIN FETCH sa.student st
                JOIN FETCH st.branch b
                LEFT JOIN FETCH st.studentClassSessions scs
                LEFT JOIN FETCH sa.attendanceCoach ac
                LEFT JOIN FETCH sa.evaluationCoach ec
                JOIN FETCH sa.classSession cs
                WHERE cs.idClassSession = :idClassSession
                  AND sa.attendanceDate = :attendanceDate
            """)
    List<StudentAttendance> findByIdClassSessionAndAttendanceDate(
            @Param("idClassSession") String idClassSession,
            @Param("attendanceDate") LocalDate attendanceDate
    );

    @Query("""
                SELECT DISTINCT sa.idUser
                FROM StudentAttendance sa
                JOIN sa.classSession cs
                WHERE cs.idClassSession = :idClassSession
                  AND sa.attendanceDate = :attendanceDate
            """)
    Set<UUID> findAttendedStudentIdsByClassSessionAndDate(
            @Param("idClassSession") String idClassSession,
            @Param("attendanceDate") LocalDate attendanceDate
    );

    Boolean existsByStudentAndClassSessionAndAttendanceDate(Student student, ClassSession classSession, LocalDate attendanceDate);

    @Query("""
            SELECT sa
            FROM StudentAttendance sa
            JOIN sa.classSession cs
            WHERE sa.student = :student
              AND YEAR(sa.attendanceDate) = :year
              AND MONTH(sa.attendanceDate) IN :months
            """)
    List<StudentAttendance> findByStudentAndYearAndQuarter(
            @Param("student") Student student,
            @Param("year") Integer year,
            @Param("months") List<Integer> months
    );

    @Query("""
            SELECT sa
            FROM StudentAttendance sa
            WHERE sa.student.idAccount = :#{#attendanceKey.idAccount}
              AND sa.idClassSession = :#{#attendanceKey.idClassSession}
              AND sa.attendanceDate = :#{#attendanceKey.attendanceDate}
            """)
    Optional<StudentAttendance> findByStudentAttendanceAccountKey(
            @Param("attendanceKey") AttendanceDTO.StudentAttendanceAccountKey attendanceKey
    );
}
