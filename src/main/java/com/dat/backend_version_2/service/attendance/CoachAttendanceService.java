package com.dat.backend_version_2.service.attendance;

import com.dat.backend_version_2.domain.attendance.CoachAttendance;
import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.domain.training.Coach;
import com.dat.backend_version_2.dto.attendance.CoachAttendanceDTO;
import com.dat.backend_version_2.dto.attendance.CoachAttendanceRes;
import com.dat.backend_version_2.mapper.attendance.CoachAttendanceMapper;
import com.dat.backend_version_2.repository.attendance.CoachAttendanceRepository;
import com.dat.backend_version_2.service.training.ClassSessionService;
import com.dat.backend_version_2.service.training.CoachService;
import com.dat.backend_version_2.specification.Helper.DateSpecification;
import com.dat.backend_version_2.specification.Helper.FieldSpecification;
import com.dat.backend_version_2.util.error.IdInvalidException;
import com.dat.backend_version_2.util.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachAttendanceService {
    private final CoachAttendanceRepository coachAttendanceRepository;
    private final CoachService coachService;
    private final ClassSessionService classSessionService;

    @Transactional
    public void markCoachAttendance(CoachAttendanceDTO.CreateRequest request) throws IdInvalidException {
        Coach coach = coachService.getCoachByIdAccount(request.getIdAccount());
        ClassSession classSession = classSessionService.getClassSessionById(request.getIdClassSession());

        CoachAttendance coachAttendance = new CoachAttendance();
        coachAttendance.setCoach(coach);
        coachAttendance.setClassSession(classSession);
        coachAttendance.setCreatedAt(request.getCreatedAt());

        if (request.getFileName() != null) {
            coachAttendance.setImageUrl(request.getFileName());
        }

        coachAttendanceRepository.save(coachAttendance);
    }

    public List<CoachAttendanceRes> getCoachAttendanceByIdCoachAndYearAndMonth(
            String idCoach, int year, int month) throws UserNotFoundException {
        Coach coach = coachService.getCoachByIdAccount(idCoach);

        List<Integer>months = Collections.singletonList(month);

        Specification<CoachAttendance> spec =
                DateSpecification.<CoachAttendance>hasMonthOfDateIn(months, "createdAt")
                        .and(DateSpecification.hasYearOfDate(year, "createdAt"))
                        .and(FieldSpecification.hasFieldEqual("coach", coach));

        List<CoachAttendance>coachAttendances = coachAttendanceRepository.findAll(spec);

        return coachAttendances.stream()
                .map(CoachAttendanceMapper::coachAttendanceToCoachAttendanceRes)
                .toList();
    }
}
