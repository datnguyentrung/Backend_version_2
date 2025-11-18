package com.dat.backend_version_2.service.training;

import com.dat.backend_version_2.domain.training.Coach;
import com.dat.backend_version_2.dto.training.Coach.CoachReq;
import com.dat.backend_version_2.mapper.training.CoachMapper;
import com.dat.backend_version_2.redis.training.CoachRedisImpl;
import com.dat.backend_version_2.repository.training.CoachRepository;
import com.dat.backend_version_2.service.authentication.UsersService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import com.dat.backend_version_2.util.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoachService {
    private final CoachRepository coachRepository;
    private final UsersService usersService;
    private final CoachRedisImpl coachRedis;

    public Coach createCoach(CoachReq.CoachInfo coachReq) {
        Coach coach = new Coach();
        CoachMapper.contactInfoToCoach(coach, coachReq.getContact());
        CoachMapper.jobInfoToCoach(coach, coachReq.getJob());
        CoachMapper.personalInfoToCoach(coach, coachReq.getPersonal());

        usersService.setupBaseUser(coach, "COACH");

        return coachRepository.save(coach);
    }

    public Coach getCoachById(UUID idUser) throws IdInvalidException {
        return coachRepository.findById(idUser)
                .orElseThrow(() -> new IdInvalidException("Coach not found with id: " + idUser));
    }

    public Coach getCoachByIdAccount(String idAccount) throws UserNotFoundException {
        Coach coach = coachRedis.getCoachByIdAccount(idAccount);

        if (coach == null) {
            coach = coachRepository.findByIdAccount(idAccount)
                    .orElseThrow(() -> new UserNotFoundException("Coach not found with id: " + idAccount));
            coachRedis.saveCoachByIdAccount(idAccount, coach);
        }
        return coach;
    }

    public Coach getAndValidateActiveCoach(UUID idUser) throws IdInvalidException {
        // 1. Dùng lại hàm get cũ
        Coach coach = getCoachById(idUser);

        // 2. Tập trung logic kiểm tra active vào đây
        if (!coach.getIsActive()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Coach no longer have permission to use this feature"
            );
        }

        return coach;
    }

    public Coach getAndValidateActiveCoach(String idAccount) throws UserNotFoundException, ResponseStatusException, IdInvalidException {
        // 1. Dùng lại hàm get cũ
        Coach coach = getCoachByIdAccount(idAccount);

        // 2. Tập trung logic kiểm tra active vào đây
        if (!coach.getIsActive()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Coach no longer have permission to use this feature"
            );
        }

        return coach;
    }
}
