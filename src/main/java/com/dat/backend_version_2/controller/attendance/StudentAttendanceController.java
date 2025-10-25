package com.dat.backend_version_2.controller.attendance;

import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import com.dat.backend_version_2.service.attendance.StudentAttendanceService;
import com.dat.backend_version_2.service.training.ClassSessionService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/student-attendance")
@RequiredArgsConstructor
public class StudentAttendanceController {
    private final StudentAttendanceService studentAttendanceService;
    private final ClassSessionService classSessionService;

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

    // Sửa điểm danh
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

    // Sửa đánh giá
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

    // Điểm danh theo mã buổi học và ngày điểm danh
    @PostMapping("/class-session")
    public ResponseEntity<Map<String, Object>> attendanceByClassSession(
            @RequestParam String idClassSession,
            @RequestParam LocalDate attendanceDate,
            Authentication authentication) throws ResponseStatusException {
        try {
            String idCoachAccount = authentication.getName();

            studentAttendanceService.createAttendancesByClassSessionAndDate(idClassSession, attendanceDate, idCoachAccount);

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

    // Lấy danh sách điểm danh ở 1 lớp học trong ngày
    @GetMapping("/class-session")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByClassSession(
            @RequestParam String idClassSession,
            @RequestParam LocalDate attendanceDate) throws IdInvalidException {

        ClassSession classSession = classSessionService
                .getClassSessionById(idClassSession);

        if (!classSession.getIsActive()) {
            throw new RuntimeException("ClassSession is not active");
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                studentAttendanceService.getAttendanceByClassSessionAndDate(idClassSession, attendanceDate));
    }
}