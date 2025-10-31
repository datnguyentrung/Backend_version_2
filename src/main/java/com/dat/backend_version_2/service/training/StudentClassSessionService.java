package com.dat.backend_version_2.service.training;

import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.domain.training.StudentClassSession;
import com.dat.backend_version_2.dto.training.StudentClassSession.StudentClassSessionReq;
import com.dat.backend_version_2.repository.training.StudentClassSessionRepository;
import com.dat.backend_version_2.util.error.IdInvalidException;
import com.dat.backend_version_2.util.error.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentClassSessionService {
    private final StudentClassSessionRepository studentClassSessionRepository;
    private final StudentService studentService;
    private final ClassSessionService classSessionService;

    public List<Student> getStudentsByClassSession(String idClassSession) {
        List<Student> studentIds = studentClassSessionRepository
                .findStudentsByClassSession(idClassSession);

        if (studentIds == null || studentIds.isEmpty()) {
            throw new UserNotFoundException(
                    "Không tìm thấy học viên nào cho classSession: " + idClassSession
            );
        }

        return studentIds;
    }

    @Transactional
    public void createStudentClassSession(StudentClassSessionReq request) {
        Student student = studentService.getStudentById(request.getIdUser());

        List<StudentClassSession> studentClassSessions = request.getIdClassSessions().stream()
                .map(idClassSession -> {
                    ClassSession classSession = null;
                    try {
                        classSession = classSessionService.getClassSessionById(idClassSession);
                    } catch (IdInvalidException | JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    if (!classSession.getIsActive()) {
                        throw new RuntimeException("ClassSession is not active: " + idClassSession);
                    }

                    StudentClassSession studentClassSession = new StudentClassSession();
                    studentClassSession.setStudent(student);
                    studentClassSession.setClassSession(classSession);
                    return studentClassSession;
                })
                .toList();
        studentClassSessionRepository.saveAll(studentClassSessions);
    }

    public Boolean getStudentClassSession(String idClassSession, String idUser) {
        return studentClassSessionRepository.existsByIdClassSessionAndIdUser(idClassSession, UUID.fromString(idUser));
    }
}
