package com.dat.backend_version_2.domain.attendance;

import com.dat.backend_version_2.domain.training.Student;
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
@Table(name = "StudentAttendance", schema = "attendance")
public class StudentAttendance extends BaseAttendance {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idAttendance;

    @ManyToOne
    @JoinColumn(name = "student_id_user")
    private Student student;
}
