package com.dat.backend_version_2.dto.attendance;

import com.dat.backend_version_2.domain.training.Coach;
import com.dat.backend_version_2.enums.attendance.AttendanceStatus;
import com.dat.backend_version_2.enums.attendance.EvaluationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@MappedSuperclass
public abstract class BaseAttendance {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus attendanceStatus = AttendanceStatus.V;

    private LocalTime attendanceTime = LocalTime.now();

    @ManyToOne
    @JoinColumn(name = "attendance_coach_id_user")
    private Coach attendanceCoach;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private EvaluationStatus evaluationStatus;

    @ManyToOne
    @JoinColumn(name = "evaluation_coach_id_user")
    private Coach evaluationCoach;

    @Column(columnDefinition = "TEXT")
    private String notes;
}