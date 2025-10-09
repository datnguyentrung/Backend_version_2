package com.dat.backend_version_2.controller.attendance;

import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import com.dat.backend_version_2.service.attendance.StudentAttendanceService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/student-attendance")
@RequiredArgsConstructor
public class StudentAttendanceController {
    private final StudentAttendanceService studentAttendanceService;

    @PostMapping
    public ResponseEntity<String> createAttendance(
            @RequestBody AttendanceDTO attendanceDTO,
            Authentication authentication) throws IdInvalidException {
        String idAccount = authentication.getName();

        studentAttendanceService.createStudentAttendance(attendanceDTO, idAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "Attendance created successfully for ID: " + attendanceDTO.getIdStudent()
        );
    }

    @PatchMapping("/attendance")
    public ResponseEntity<String> markAttendance(
            @RequestBody AttendanceDTO.MarkAttendance markAttendance,
            Authentication authentication) throws IdInvalidException, ResponseStatusException {
        String idAccount = authentication.getName();

        studentAttendanceService.markAttendance(markAttendance, idAccount);
        return ResponseEntity.ok(
                "Attendance updated successfully for ID: " + markAttendance.getIdAttendance()
        );
    }

    @PatchMapping("/evaluation")
    public ResponseEntity<String> markEvaluation(
            @RequestBody AttendanceDTO.MarkEvaluation markEvaluation,
            Authentication authentication) throws IdInvalidException, ResponseStatusException {
        String idAccount = authentication.getName();

        studentAttendanceService.markEvaluation(markEvaluation, idAccount);
        return ResponseEntity.ok(
                "Evaluation updated successfully for ID: " + markEvaluation.getIdAttendance()
        );
    }

    @PostMapping("/class-session/{idClassSession}")
    public ResponseEntity<Map<String, Object>> attendanceByClassSession(
            @PathVariable String idClassSession) {
        try {
            studentAttendanceService.attendanceByClassSession(idClassSession);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Điểm danh thành công");
            response.put("idClassSession", idClassSession);

            return ResponseEntity.ok(response);
        } catch (RuntimeException | IdInvalidException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

    @GetMapping("/class-session/{idClassSession}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByClassSession(@PathVariable String idClassSession) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.OK).body(
                studentAttendanceService.getCurrentAttendanceByClassSession(idClassSession));
    }
}