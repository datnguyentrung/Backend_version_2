package com.dat.backend_version_2.domain.attendance;

import com.dat.backend_version_2.domain.registration.Registration;
import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import com.dat.backend_version_2.dto.attendance.BaseAttendance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TrialAttendance", schema = "attendance")
@IdClass(AttendanceDTO.TrialAttendanceKey.class)
public class TrialAttendance extends BaseAttendance {
    @Id
    @Column(name = "registration_id_registration")
    private UUID idRegistration;

    @Id
    @Column(name = "class_session")
    private String idClassSession;

    @Id
    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "registration_id_registration",
            insertable = false,
            updatable = false
    )
    private Registration registration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_session", insertable = false, updatable = false)
    private ClassSession classSession;
}