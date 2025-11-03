package com.dat.backend_version_2.domain.attendance;

import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.domain.training.Coach;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import com.dat.backend_version_2.listener.attendance.CoachAttendanceListener;
import com.dat.backend_version_2.util.ConverterUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CoachAttendance", schema = "attendance")
@IdClass(AttendanceDTO.CoachAttendanceKey.class)
@EntityListeners(CoachAttendanceListener.class)
public class CoachAttendance {
    @Id
    @Column(name = "coach_id_user")
    private UUID idUser;

    @Id
    @Column(name = "class_session")
    private String idClassSession;

    @Id
    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        this.attendanceDate = ConverterUtils.localDateTimeToLocalDate(createdAt);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id_user", insertable = false, updatable = false)
    private Coach coach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_session", insertable = false, updatable = false)
    private ClassSession classSession;

    private String imageUrl;
}
