package com.dat.backend_version_2.dto.attendance;

import com.dat.backend_version_2.domain.training.ClassSession;
import lombok.Data;

import java.time.Instant;

@Data
public class CoachAttendanceRes {
    private String name;

    private Instant createdAt;
    private ClassSession classSession;
}
