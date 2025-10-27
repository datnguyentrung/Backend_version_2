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
            @Index(name = "idx_class_session", columnList = "class_session")
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

    @MapsId("idUser")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id_user")
    private Student student;

    @MapsId("idClassSession")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_session")
    private ClassSession classSession;
}
