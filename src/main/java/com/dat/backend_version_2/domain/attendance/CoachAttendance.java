package com.dat.backend_version_2.domain.attendance;

import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.domain.training.Coach;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CoachAttendance", schema = "attendance")
public class CoachAttendance {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idAttendance;

    @ManyToOne
    @JoinColumn(name = "coach_id_user")
    private Coach coach;

    private LocalDateTime attendanceDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "class_session")
    private ClassSession classSession;
}
