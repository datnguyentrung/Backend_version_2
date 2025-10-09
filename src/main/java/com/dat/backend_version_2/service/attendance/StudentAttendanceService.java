package com.dat.backend_version_2.service.attendance;

import com.dat.backend_version_2.domain.attendance.StudentAttendance;
import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.domain.training.Coach;
import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.dto.attendance.AttendanceDTO;
import com.dat.backend_version_2.enums.attendance.AttendanceStatus;
import com.dat.backend_version_2.mapper.attendance.StudentAttendanceMapper;
import com.dat.backend_version_2.repository.attendance.StudentAttendanceRepository;
import com.dat.backend_version_2.service.training.ClassSessionService;
import com.dat.backend_version_2.service.training.CoachService;
import com.dat.backend_version_2.service.training.StudentClassSessionService;
import com.dat.backend_version_2.service.training.StudentService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentAttendanceService {
    private final StudentAttendanceRepository studentAttendanceRepository;
    private final StudentClassSessionService studentClassSessionService;
    private final ClassSessionService classSessionService;
    private final CoachService coachService;
    private final StudentService studentService;

    // Tìm bản điểm danh theo id
    public StudentAttendance getStudentAttendanceById(String idAttendance) throws IdInvalidException {
        return studentAttendanceRepository.findById(UUID.fromString(idAttendance))
                .orElseThrow(() -> new IdInvalidException("StudentAttendance not found with id: " + idAttendance));
    }

    public boolean hasAttendance(
            Student student, ClassSession classSession, LocalDate attendanceDate){
        return studentAttendanceRepository.existsByStudentAndClassSessionAndAttendanceDate(student, classSession, attendanceDate);
    }

    // Điểm danh theo mã lớp học
    @Transactional
    public void attendanceByClassSession(String idClassSession) throws IdInvalidException {
        ClassSession classSession = classSessionService
                .getClassSessionById(idClassSession);

        if (!classSession.getIsActive()) {
            throw new RuntimeException("ClassSession is not active");
        }

        List<Student> students = studentClassSessionService
                .getStudentsByClassSession(idClassSession);

        List<AttendanceDTO> attendanceDTOS = getCurrentAttendanceByClassSession(idClassSession);

        Set<String> attendanceSet = new HashSet<>(
                attendanceDTOS.stream().map(AttendanceDTO::getIdStudent).toList()
        );

        List<StudentAttendance> attendances = students.stream()
                .filter(student -> !attendanceSet.contains(student.getIdAccount()))
                .map(student -> {
                    StudentAttendance attendance = new StudentAttendance();
                    attendance.setStudent(student);
                    attendance.setClassSession(classSession);
                    return attendance;
                })
                .toList();
        if (!attendances.isEmpty()) {
            studentAttendanceRepository.saveAll(attendances);
        }
    }

    // Lấy danh sách điểm danh ở 1 lớp học trong ngày
    public List<AttendanceDTO> getCurrentAttendanceByClassSession(String idClassSession) throws IdInvalidException {
        ClassSession classSession = classSessionService
                .getClassSessionById(idClassSession);

        if (!classSession.getIsActive()) {
            throw new RuntimeException("ClassSession is not active");
        }

        return studentAttendanceRepository.findByIdClassSessionAndAttendanceDate(idClassSession, LocalDate.now()).stream()
                .map(StudentAttendanceMapper::studentAttendanceToAttendanceDTO)
                .toList();
    }

    // Sửa cột "Điểm danh" trong 1 bản ghi
    // idAccount: mã Huấn Luyện Viên điểm danh
    @Transactional
    public void markAttendance(AttendanceDTO.MarkAttendance markAttendance, String idAccount
    ) throws IdInvalidException, ResponseStatusException {
        StudentAttendance studentAttendance = getStudentAttendanceById(markAttendance.getIdAttendance());

        Coach coach = coachService.getCoachById(idAccount);
        if (!coach.getIsActive()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Coach no longer have permission to use this feature"
            );
        }

        studentAttendance.setAttendanceStatus(markAttendance.getAttendanceStatus());
        if (markAttendance.getAttendanceStatus() == AttendanceStatus.V ||
                markAttendance.getAttendanceStatus() == AttendanceStatus.P){
            studentAttendance.setEvaluationCoach(null);
            studentAttendance.setEvaluationStatus(null);
        } else {
            studentAttendance.setAttendanceTime(LocalDateTime.now());
            studentAttendance.setAttendanceCoach(coach);
            studentAttendanceRepository.save(studentAttendance);
        }
    }

    // Sửa cột "Đánh giá" trong 1 bản ghi
    // idAccount: mã Huấn Luyện Viên đánh giá
    @Transactional
    public void markEvaluation(AttendanceDTO.MarkEvaluation markEvaluation, String idAccount
    ) throws IdInvalidException, ResponseStatusException {
        StudentAttendance studentAttendance = getStudentAttendanceById(markEvaluation.getIdAttendance());

        if (studentAttendance.getAttendanceStatus()== AttendanceStatus.V || studentAttendance.getAttendanceStatus()== AttendanceStatus.P) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "EvaluationStatus is not allowed when AttendanceStatus is V or P"
            );
        }

        Coach coach = coachService.getCoachById(idAccount);
        if (!coach.getIsActive()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Coach no longer have permission to use this feature"
            );
        }

        studentAttendance.setEvaluationStatus(markEvaluation.getEvaluationStatus());
        studentAttendance.setEvaluationCoach(coach);
        studentAttendanceRepository.save(studentAttendance);
    }

    // Tạo bản điểm danh mới
    // idAccount: mã Huấn Luyện Viên đánh giá
    @Transactional
    public void createStudentAttendance(AttendanceDTO attendanceDTO, String idAccount) throws IdInvalidException {
        ClassSession classSession = classSessionService
                .getClassSessionById(attendanceDTO.getIdClassSession());
        if (!classSession.getIsActive()) {
            throw new RuntimeException("ClassSession is not active: " + attendanceDTO.getIdClassSession());
        }

        Student student = studentService.getStudentByIdAccount(attendanceDTO.getIdStudent());
        if (!student.getIsActive()) {
            throw new RuntimeException("Student is not active: " + attendanceDTO.getIdStudent());
        }

        Coach coach = coachService.getCoachById(idAccount);
        if (!coach.getIsActive()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Coach no longer have permission to use this feature"
            );
        }

        if (hasAttendance(student, classSession, LocalDate.now())) {
            throw new DuplicateKeyException("Attendance already exists for this student");
        }

        StudentAttendance studentAttendance = StudentAttendanceMapper
                .attendanceInfoToStudentAttendance(attendanceDTO.getAttendanceInfo());
        studentAttendance.setStudent(student);
        studentAttendance.setClassSession(classSession);
        studentAttendance.setAttendanceCoach(coach);

        if (attendanceDTO.getAttendanceInfo().getEvaluationStatus() != null) {
            studentAttendance.setEvaluationCoach(coach);
        }
        studentAttendanceRepository.save(studentAttendance);
    }
}
