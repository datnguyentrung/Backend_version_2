package com.dat.backend_version_2.domain.attendance;

import com.dat.backend_version_2.domain.registration.Registration;
import com.dat.backend_version_2.dto.attendance.BaseAttendance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TrialAttendance", schema = "attendance")
public class TrialAttendance extends BaseAttendance {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idAttendance;

    @ManyToOne
    @JoinColumn(name = "registration_id_registration")
    private Registration registration;
}