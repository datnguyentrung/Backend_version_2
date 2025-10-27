package com.dat.backend_version_2.service.registration;

import com.dat.backend_version_2.domain.registration.Registration;
import com.dat.backend_version_2.domain.training.Branch;
import com.dat.backend_version_2.dto.registration.RegistrationDTO;
import com.dat.backend_version_2.enums.registration.RegistrationStatus;
import com.dat.backend_version_2.mapper.registration.RegistrationMapper;
import com.dat.backend_version_2.repository.registration.RegistrationRepository;
import com.dat.backend_version_2.service.training.BranchService;
import com.dat.backend_version_2.service.training.StudentService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final BranchService branchService;
    private final StudentService studentService;

    @Transactional
    public void createRegistration(RegistrationDTO.PersonalInfo personalInfo) {
        registrationRepository.findByNameAndPhone(personalInfo.getName(), personalInfo.getPhone())
                .ifPresent(existing -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Registration already exists for name: " + personalInfo.getName() +
                                    " and phone: " + personalInfo.getPhone()
                    );
                });

        Registration registration = RegistrationMapper.personalInfoToRegistration(personalInfo);
        registrationRepository.save(registration);
    }

    public List<RegistrationDTO> getAllRegistrations() {
        return registrationRepository.findAllWithBranch().stream()
                .map(RegistrationMapper::registrationToRegistrationDTO)
                .toList();
    }

    public Registration getRegistrationById(String idRegistration) throws IdInvalidException {
        return registrationRepository.findById(UUID.fromString(idRegistration))
                .orElseThrow(() -> new IdInvalidException("Không tìm thấy registration với ID: " + idRegistration));
    }

    public void updateRegistration(
            String idRegistration,
            RegistrationDTO.PersonalInfo personalInfo) throws IdInvalidException {
        Registration registration = getRegistrationById(idRegistration);
        RegistrationMapper.updatePersonalInfo(registration, personalInfo);
        registrationRepository.save(registration);
    }

    public void registerRegistration(RegistrationDTO registrationDTO) throws IdInvalidException, JsonProcessingException {
        Registration registration = getRegistrationById(registrationDTO.getIdRegistration());

        Branch branch = branchService.getBranchById(registrationDTO.getRegistrationInfo().getIdBranch());

        registration.setBranch(branch);
        registration.setRegistrationDate(LocalDate.now());
        registration.setRegistrationStatus(RegistrationStatus.REGISTERED);

        registrationRepository.save(registration);

        studentService.createStudent(RegistrationMapper.registrationDTOToStudentReqStudentInfo(registrationDTO));
    }
}