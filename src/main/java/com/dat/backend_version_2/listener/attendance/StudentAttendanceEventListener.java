package com.dat.backend_version_2.listener.attendance;

import com.dat.backend_version_2.dto.attendance.StudentAttendanceDTO;
import com.dat.backend_version_2.service.attendance.StudentAttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudentAttendanceEventListener {
    private final StudentAttendanceService studentAttendanceService;

    @EventListener
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleStudentAttendanceEvent(StudentAttendanceDTO.StudentAttendanceClassSession event) {
        try {
            log.info("🎯 handleStudentAttendanceEvent started for class: {}, date: {}",
                    event.getIdClassSession(), event.getAttendanceDate());

            studentAttendanceService.createAttendancesInternal(event);

            log.info("✅ handleStudentAttendanceEvent completed successfully");
        } catch (Exception e) {
            log.error("❌ Error in handleStudentAttendanceEvent: {}", e.getMessage(), e);
            throw e; // Re-throw to ensure proper error handling
        }
    }
}
