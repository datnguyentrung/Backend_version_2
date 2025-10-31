package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.domain.training.Coach;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CoachRedis {
    void clear();

    Coach getCoachByIdAccount(String idAccount) throws JsonProcessingException;

    void saveCoachByIdAccount(String idAccount, Coach coach) throws JsonProcessingException;
}
