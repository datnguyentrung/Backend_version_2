package com.dat.backend_version_2.controller.training;

import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.dto.training.Student.StudentReq;
import com.dat.backend_version_2.dto.training.Student.StudentRes;
import com.dat.backend_version_2.mapper.training.StudentMapper;
import com.dat.backend_version_2.redis.training.StudentRedisImpl;
import com.dat.backend_version_2.service.training.StudentService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    private final StudentRedisImpl studentRedis;

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
    public ResponseEntity<List<StudentRes.PersonalAcademicInfo>> getAllStudents()
            throws JsonProcessingException {
        List<StudentRes.PersonalAcademicInfo> students = studentRedis.getAllStudents();
        if (students == null) {
            students = studentService.getAllStudents();
            studentRedis.saveAllStudents(students);
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/branch/{idBranch}")
    public ResponseEntity<List<StudentRes.PersonalAcademicInfo>> getStudentByBranch(
            @PathVariable Integer idBranch) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.getStudentsByIdBranch(idBranch));
    }

    @GetMapping("/class-session/{idClassSession}")
    public ResponseEntity<List<StudentRes.PersonalAcademicInfo>> getStudentByClassSession(
            @PathVariable String idClassSession) throws IdInvalidException, JsonProcessingException {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(studentService.getStudentsByIdClassSession(idClassSession));
        List<StudentRes.PersonalAcademicInfo> students = studentRedis.getStudentsByIdClassSession(idClassSession);
        if (students == null) {
            students = studentService.getStudentsByIdClassSession(idClassSession);
            studentRedis.saveStudentsByIdClassSession(idClassSession, students);
        }
        return ResponseEntity.ok(students);
    }
}