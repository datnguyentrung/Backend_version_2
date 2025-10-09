package com.dat.backend_version_2.mapper.registration;

import com.dat.backend_version_2.domain.registration.Registration;
import com.dat.backend_version_2.domain.training.Branch;
import com.dat.backend_version_2.dto.registration.RegistrationDTO;
import com.dat.backend_version_2.dto.training.Student.StudentReq;
import com.dat.backend_version_2.enums.training.BeltLevel;

import java.time.LocalDate;
import java.util.Optional;

public class RegistrationMapper {
    public static Registration personalInfoToRegistration(RegistrationDTO.PersonalInfo personalInfo) {
        if (personalInfo == null) {
            return null;
        }
        Registration registration = new Registration();
        registration.setName(personalInfo.getName());
        registration.setBirthDate(personalInfo.getBirthDate());
        registration.setPhone(personalInfo.getPhone());
        registration.setReferredBy(personalInfo.getReferredBy());
        return registration;
    }

    public static RegistrationDTO registrationToRegistrationDTO(Registration registration) {
        if (registration == null) {
            return null;
        }
        RegistrationDTO registrationDTO = new RegistrationDTO();
        RegistrationDTO.RegistrationInfo registrationInfo = new RegistrationDTO.RegistrationInfo();
        RegistrationDTO.PersonalInfo personalInfo = new RegistrationDTO.PersonalInfo();

        registrationDTO.setIdRegistration(registration.getIdRegistration().toString());

        personalInfo.setName(registration.getName());
        personalInfo.setBirthDate(registration.getBirthDate());
        personalInfo.setPhone(registration.getPhone());
        personalInfo.setReferredBy(registration.getReferredBy());

        registrationInfo.setIdBranch(
                Optional.ofNullable(registration.getBranch())
                        .map(Branch::getIdBranch)
                        .orElse(null)
        );
        registrationInfo.setRegistrationDate(registration.getRegistrationDate());
        registrationInfo.setRegistrationStatus(registration.getRegistrationStatus());

        registrationDTO.setPersonalInfo(personalInfo);
        registrationDTO.setRegistrationInfo(registrationInfo);

        return registrationDTO;
    }

    public static void updatePersonalInfo(Registration registration, RegistrationDTO.PersonalInfo personalInfo) {
        registration.setName(personalInfo.getName());
        registration.setBirthDate(personalInfo.getBirthDate());
        registration.setPhone(personalInfo.getPhone());
        registration.setReferredBy(personalInfo.getReferredBy());
    }

    public static StudentReq.PersonalInfo registrationDTOToStudentReqPersonalInfo(RegistrationDTO registrationDTO) {
        if (registrationDTO == null) {
            return null;
        }
        StudentReq.PersonalInfo personalInfo = new StudentReq.PersonalInfo();
        personalInfo.setName(registrationDTO.getPersonalInfo().getName());
        personalInfo.setIdUser(registrationDTO.getIdRegistration());
        personalInfo.setBirthDate(registrationDTO.getPersonalInfo().getBirthDate());
        return personalInfo;
    }

    public static StudentReq.ContactInfo registrationDTOToStudentReqContactInfo(RegistrationDTO registrationDTO) {
        if (registrationDTO == null) {
            return null;
        }
        StudentReq.ContactInfo contactInfo = new StudentReq.ContactInfo();
        contactInfo.setPhone(registrationDTO.getPersonalInfo().getPhone());
        return contactInfo;
    }

    public static StudentReq.AcademicInfo registrationDTOToStudentReqAcademicInfo(RegistrationDTO registrationDTO) {
        if (registrationDTO == null) {
            return null;
        }
        StudentReq.AcademicInfo academicInfo = new StudentReq.AcademicInfo();
        academicInfo.setIdBranch(registrationDTO.getRegistrationInfo().getIdBranch());
        academicInfo.setBeltLevel(
                Optional.ofNullable(registrationDTO.getRegistrationInfo().getBeltLevel())
                .orElse(BeltLevel.C10)
        );
        academicInfo.setIsActive(true);
        academicInfo.setClassSession(registrationDTO.getRegistrationInfo().getIdClassSession());
        return academicInfo;
    }

    public static StudentReq.StudentInfo registrationDTOToStudentReqStudentInfo(RegistrationDTO registrationDTO) {
        if (registrationDTO == null) {
            return null;
        }

        StudentReq.StudentInfo studentInfo = new StudentReq.StudentInfo();
        studentInfo.setPersonal(registrationDTOToStudentReqPersonalInfo(registrationDTO));
        studentInfo.setContact(registrationDTOToStudentReqContactInfo(registrationDTO));
        studentInfo.setAcademic(registrationDTOToStudentReqAcademicInfo(registrationDTO));

        // Tạo EnrollmentInfo mặc định
        StudentReq.EnrollmentInfo enrollment = new StudentReq.EnrollmentInfo();
        enrollment.setStartDate(LocalDate.now());   // mặc định hôm nay
        enrollment.setEndDate(null);                // chưa xác định
        studentInfo.setEnrollment(enrollment);

        return studentInfo;
    }
}
