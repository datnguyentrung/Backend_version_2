package com.dat.backend_version_2.controller.attendance;

import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import com.dat.backend_version_2.dto.attendance.StudentAttendanceDTO;
import com.dat.backend_version_2.service.attendance.StudentAttendanceService;
import com.dat.backend_version_2.service.training.ClassSessionService;
import com.dat.backend_version_2.util.SecurityUtil;
import com.dat.backend_version_2.util.error.IdInvalidException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/student-attendance")
@RequiredArgsConstructor
public class StudentAttendanceController {
    private final StudentAttendanceService studentAttendanceService;
    private final ClassSessionService classSessionService;

    @PostMapping
    public ResponseEntity<String> createAttendance(
            @RequestBody StudentAttendanceDTO.CreateStudentAttendance attendanceDTO,
            Authentication authentication) throws IdInvalidException, JsonProcessingException {
        String idAccount = authentication.getName();

        studentAttendanceService.createStudentAttendance(attendanceDTO, idAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "Attendance created successfully for key: " + attendanceDTO.getAttendanceKey()
        );
    }

    // Sửa điểm danh
    @PatchMapping("/attendance")
    public ResponseEntity<String> markAttendance(
            @RequestBody StudentAttendanceDTO.StudentMarkAttendance markAttendance,
            Authentication authentication) throws IdInvalidException, ResponseStatusException {
        String idAccount = authentication.getName();

        // Lấy status từ JWT token
        Optional<String> userStatus = SecurityUtil.getCurrentUserStatus();
        if (userStatus.isPresent()) {
            String status = userStatus.get();
            System.out.println("User status: " + status);

            // Kiểm tra status nếu cần
            if (!"ACTIVE".equals(status)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User account is not active. Status: " + status);
            }
        }

        studentAttendanceService.markAttendance(markAttendance, idAccount);
        return ResponseEntity.ok(
                "Attendance updated successfully for key: " + markAttendance.getAttendanceKey()
        );
    }

    // Sửa đánh giá
    @PatchMapping("/evaluation")
    public ResponseEntity<String> markEvaluation(
            @RequestBody StudentAttendanceDTO.StudentMarkEvaluation markEvaluation,
            Authentication authentication) throws IdInvalidException, ResponseStatusException {
        String idAccount = authentication.getName();

        studentAttendanceService.markEvaluation(markEvaluation, idAccount);
        return ResponseEntity.ok(
                "Evaluation updated successfully for key: " + markEvaluation.getAttendanceKey()
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

            studentAttendanceService.createAttendancesByClassSessionAndDate(
                    idClassSession, attendanceDate, idCoachAccount);

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
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy danh sách điểm danh ở 1 lớp học trong ngày
    @GetMapping("/class-session")
    public ResponseEntity<List<StudentAttendanceDTO.StudentAttendanceDetail>> getAttendanceByClassSession(
            @RequestParam String idClassSession,
            @RequestParam LocalDate attendanceDate) throws IdInvalidException {

        return ResponseEntity.status(HttpStatus.OK).body(
                studentAttendanceService.getAttendanceByClassSessionAndDate(idClassSession, attendanceDate));
    }
}