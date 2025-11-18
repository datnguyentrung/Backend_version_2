package com.dat.backend_version_2.domain.attendance;

import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import com.dat.backend_version_2.dto.attendance.BaseAttendance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "StudentAttendance", schema = "attendance",
        indexes = {
            @Index(name = "idx_session_date", columnList = "class_session, attendance_date")
        }
)
@IdClass(AttendanceDTO.StudentAttendanceKey.class)
public class StudentAttendance extends BaseAttendance {
    @Id
    @Column(name = "student_id_user")
    private UUID idUser;

    @Id
    @Column(name = "class_session")
    private String idClassSession;

    @Id
    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id_user", insertable = false, updatable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_session", insertable = false, updatable = false)
    private ClassSession classSession;
}
