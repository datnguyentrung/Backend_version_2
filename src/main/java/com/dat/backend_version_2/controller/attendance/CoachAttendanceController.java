package com.dat.backend_version_2.controller.attendance;

import com.dat.backend_version_2.dto.attendance.CoachAttendanceRes;
import com.dat.backend_version_2.service.attendance.CoachAttendanceService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import com.dat.backend_version_2.util.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coach-attendance")
@RequiredArgsConstructor
public class CoachAttendanceController {
    private final CoachAttendanceService coachAttendanceService;

    @PostMapping("/class-session/{idClassSession}")
    public ResponseEntity<String> createCoachAttendance(
            @PathVariable String idClassSession,
            Authentication authentication) throws IdInvalidException {

        String idUser = authentication.getName();
        coachAttendanceService.markCoachAttendance(idUser, idClassSession);

        // Trả về message thành công
        return ResponseEntity.ok("Điểm danh huấn luyện viên thành công");
    }

    @GetMapping("/{idCoach}/year-month")
    public ResponseEntity<List<CoachAttendanceRes>> getCoachAttendanceByYearAndMonth(
            @PathVariable String idCoach,
            @RequestParam Integer year,
            @RequestParam Integer month) throws UserNotFoundException {
        return ResponseEntity.ok(
                coachAttendanceService.getCoachAttendanceByIdCoachAndYearAndMonth(
                        idCoach, year, month));
    }
}
