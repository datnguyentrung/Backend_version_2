package com.dat.backend_version_2.listener.attendance;

import com.dat.backend_version_2.domain.attendance.CoachAttendance;
import com.dat.backend_version_2.redis.attendance.CoachAttendanceRedis;
import jakarta.persistence.PostPersist;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

@AllArgsConstructor
public class CoachListener {
    private final CoachAttendanceRedis coachAttendanceRedis;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(CoachListener.class);

    @PostPersist
    public void postPersist(CoachAttendance coachAttendance) {

        coachAttendanceRedis.clear();
    }
}
