package com.dat.backend_version_2.service.training;

import com.dat.backend_version_2.domain.training.Coach;
import com.dat.backend_version_2.dto.training.Coach.CoachReq;
import com.dat.backend_version_2.mapper.training.CoachMapper;
import com.dat.backend_version_2.repository.training.CoachRepository;
import com.dat.backend_version_2.service.authentication.UsersService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import com.dat.backend_version_2.util.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoachService {
    private final CoachRepository coachRepository;
    private final UsersService usersService;

    public Coach createCoach(CoachReq.CoachInfo coachReq) {
        Coach coach = new Coach();
        CoachMapper.contactInfoToCoach(coach, coachReq.getContact());
        CoachMapper.jobInfoToCoach(coach, coachReq.getJob());
        CoachMapper.personalInfoToCoach(coach, coachReq.getPersonal());

        usersService.setupBaseUser(coach, "COACH");

        return coachRepository.save(coach);
    }

    public Coach getCoachById(String idUser) throws IdInvalidException {
        return coachRepository.findById(UUID.fromString(idUser))
                .orElseThrow(() -> new IdInvalidException("Coach not found with id: " + idUser));
    }

    public Coach getCoachByIdAccount(String idAccount) throws UserNotFoundException {
        return coachRepository.findByIdAccount(idAccount)
                .orElseThrow(() -> new UserNotFoundException("Coach not found with id: " + idAccount));
    }
}
