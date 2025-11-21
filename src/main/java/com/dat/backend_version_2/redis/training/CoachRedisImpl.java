package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.config.CacheTtlConfig;
import com.dat.backend_version_2.domain.training.Coach;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CoachRedisImpl implements CoachRedis {
    private final StringRedisTemplate stringRedisTemplate;
    private final CacheTtlConfig cacheTtlConfig;
    private final ObjectMapper redisObjectMapper;
    private final RedisConnectionFactory redisConnectionFactory;

    @Override
    public void clear() {
        if (redisConnectionFactory != null) {
            try (var connection = redisConnectionFactory.getConnection()) {
                connection.serverCommands().flushDb(); // chỉ xóa DB hiện tại
            }
        }
    }

    @Override
    public Coach getCoachByIdAccount(String idAccount) {
        String key = "coach:" + idAccount;
        String json = stringRedisTemplate.opsForValue().get(key);

        try {
            return json != null ?
                    redisObjectMapper.readValue(json, Coach.class)
                    : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void saveCoachByIdAccount(String idAccount, Coach coach) {
        String key = "coach:" + idAccount;

        try{
            String json = redisObjectMapper.writeValueAsString(coach);
            stringRedisTemplate.opsForValue().set(
                    key,
                    json,
                    Duration.ofMinutes(cacheTtlConfig.getOneMonthSeconds())
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
