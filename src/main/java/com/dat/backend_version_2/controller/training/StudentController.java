package com.dat.backend_version_2.controller.training;

import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.dto.training.Student.StudentReq;
import com.dat.backend_version_2.dto.training.Student.StudentRes;
import com.dat.backend_version_2.mapper.training.StudentMapper;
import com.dat.backend_version_2.service.training.StudentService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentRes.PersonalInfo> createStudent(
            @Valid @RequestBody StudentReq.StudentInfo studentInfo) throws IdInvalidException {
        Student student = studentService.createStudent(studentInfo);
        StudentRes.PersonalInfo personalInfo = StudentMapper.studentToPersonalInfo(student);

        URI location = URI.create("/api/v1/students/" + student.getIdAccount()); // hoặc idStudent

        return ResponseEntity
                .created(location) // HTTP 201 + header "Location"
                .body(personalInfo);
    }

    @GetMapping
    public ResponseEntity<List<StudentRes.PersonalAcademicInfo>> getAllStudents() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.getAllStudents());
    }

    @GetMapping("/branch/{idBranch}")
    public ResponseEntity<List<StudentRes.PersonalAcademicInfo>> getStudentByBranch(
            @PathVariable Integer idBranch) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.getStudentsByIdBranch(idBranch));
    }

    @GetMapping("/class-session/{idClassSession}")
    public ResponseEntity<List<StudentRes.PersonalAcademicInfo>> getStudentByClassSession(
            @PathVariable String idClassSession) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.getStudentsByIdClassSession(idClassSession));
    }
}