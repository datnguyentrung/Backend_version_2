package com.dat.backend_version_2.repository.attendance;

import com.dat.backend_version_2.domain.attendance.TrialAttendance;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrialAttendanceRepository extends JpaRepository<TrialAttendance, AttendanceDTO.TrialAttendanceKey> {
    @Query("""
            SELECT DISTINCT ta
            FROM TrialAttendance ta
            JOIN FETCH ta.registration
            JOIN FETCH ta.classSession
            WHERE ta.attendanceDate = :attendanceDate
            """)
    List<TrialAttendance> findAllWithRegistrationAndClassSessionByAttendanceDate(
            @Param("attendanceDate") LocalDate attendanceDate
    );
}
