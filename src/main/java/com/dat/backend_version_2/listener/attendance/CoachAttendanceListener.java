package com.dat.backend_version_2.listener.attendance;

import com.dat.backend_version_2.domain.attendance.CoachAttendance;
import com.dat.backend_version_2.redis.attendance.CoachAttendanceRedis;
import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
public class CoachAttendanceListener {

    private static CoachAttendanceRedis coachAttendanceRedis;

    @Autowired
    public void setCoachAttendanceRedis(CoachAttendanceRedis redis) {
        coachAttendanceRedis = redis;
    }
}