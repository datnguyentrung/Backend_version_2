package com.dat.backend_version_2.service.training;

import com.dat.backend_version_2.domain.training.Branch;
import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.domain.training.StudentClassSession;
import com.dat.backend_version_2.dto.training.Student.StudentReq;
import com.dat.backend_version_2.dto.training.Student.StudentRes;
import com.dat.backend_version_2.enums.training.BeltLevel;
import com.dat.backend_version_2.mapper.training.StudentMapper;
import com.dat.backend_version_2.repository.training.BranchRepository;
import com.dat.backend_version_2.repository.training.StudentRepository;
import com.dat.backend_version_2.service.authentication.UsersService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import com.dat.backend_version_2.util.error.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final BranchService branchService;
    private final UsersService usersService;
    private final ClassSessionService classSessionService;

    public Student createStudent(StudentReq.StudentInfo studentInfo) throws IdInvalidException, JsonProcessingException {
        Student student = new Student();

        // Academic Info
        Branch branch = branchService.getBranchById(studentInfo.getAcademic().getIdBranch());
        student.setBranch(branch);
        student.setBeltLevel(
                Optional.ofNullable(studentInfo.getAcademic().getBeltLevel())
                        .orElse(BeltLevel.C10)
        );
        student.setIsActive(
                Optional.ofNullable(studentInfo.getAcademic().getIsActive())
                        .orElse(true)
        );

        // Personal Info
        StudentMapper.personalInfoToStudent(studentInfo.getPersonal(), student);
        // Contact Info
        StudentMapper.contactInfoToStudent(studentInfo.getContact(), student);
        // Enrollment Info
        StudentMapper.enrollmentInfoToStudent(studentInfo.getEnrollment(), student);

        // Gọi hàm setup chung cho Users
        usersService.setupBaseUser(student, "STUDENT");

        // Lưu student
        return studentRepository.save(student);
    }

    public List<StudentRes.PersonalAcademicInfo> getAllStudents() {
        return studentRepository.findAllWithBranchAndSessions().stream()
                .map(StudentMapper::studentToPersonalAcademicInfo) // tái sử dụng hàm đã viết
                .toList();
    }

    public Student getStudentById(String idUser) {
        return studentRepository.findById(UUID.fromString(idUser))
                .orElseThrow(() -> new UserNotFoundException(
                        "Không tìm thấy học viên với id: " + idUser
                ));
    }

    public List<StudentRes.PersonalAcademicInfo> getStudentsByIdBranch(Integer idBranch) throws IdInvalidException, JsonProcessingException {
        Branch branch = branchService.getBranchById(idBranch);
        return studentRepository.findByBranch(branch).stream()
                .map(StudentMapper::studentToPersonalAcademicInfo)
                .toList();
    }

    public List<StudentRes.PersonalAcademicInfo> getStudentsByIdClassSession(String idClassSession) throws IdInvalidException, JsonProcessingException {
        ClassSession classSession = classSessionService.getClassSessionById(idClassSession);

        if (!classSession.getIsActive()) {
            throw new RuntimeException("ClassSession is not active");
        }

        return studentRepository.findAllByIdClassSession(classSession).stream()
                .map(StudentMapper::studentToPersonalAcademicInfo)
                .toList();
    }

    public Student getStudentByIdAccount(String idAccount) {
        return studentRepository.findByIdAccount(idAccount)
                .orElseThrow(() -> new UserNotFoundException("Student not found with id: " + idAccount));
    }
}
