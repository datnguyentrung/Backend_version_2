package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.config.CacheTtlConfig;
import com.dat.backend_version_2.domain.training.Coach;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CoachRedisImpl implements CoachRedis {
    private final RedisTemplate<String, Object> redisTemplate;
    private final CacheTtlConfig cacheTtlConfig;
    private final ObjectMapper redisObjectMapper;

    @Override
    public void clear() {
        var connectionFactory = redisTemplate.getConnectionFactory();
        if (connectionFactory != null) {
            try (var connection = connectionFactory.getConnection()) {
                connection.serverCommands().flushDb(); // chỉ xóa DB hiện tại
            }
        }
    }

    @Override
    public Coach getCoachByIdAccount(String idAccount) {
        String key = "coach:" + idAccount;
        String json = (String) redisTemplate.opsForValue().get(key);

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

        var ttl = cacheTtlConfig.getOneMonthSeconds();
        redisTemplate.opsForValue().set(key, coach, ttl);
    }
}
