package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.config.CacheTtlConfig;
import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.dto.training.ClassSession.ClassSessionRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ClassSessionRedisImpl implements ClassSessionRedis {
    private final RedisTemplate<String, Object> redisTemplate;
    private final CacheTtlConfig cacheTtlConfig;
    private final ObjectMapper redisObjectMapper;

    @Override
    public List<ClassSessionRes> getAllClassSessions() throws JsonProcessingException {
        String key = "classSessions:all";
        String json = (String) redisTemplate.opsForValue().get(key);
        var ttl = cacheTtlConfig.getOneMonthSeconds();

        try {
            return json != null ?
                    redisObjectMapper.readValue(json, redisObjectMapper.getTypeFactory().constructCollectionType(List.class, ClassSessionRes.class))
                    : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
    // Save all class sessions to Redis
    public void saveAllClassSessions(List<ClassSessionRes> classSessions) {
        String key = "classSessions:all";
        try {
            String json = redisObjectMapper.writeValueAsString(classSessions);
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClassSession getClassSessionById(String idClassSession) throws JsonProcessingException {
        String key = "classSession:" + idClassSession;
        String json = (String) redisTemplate.opsForValue().get(key);

        try {
            return json != null ?
                    redisObjectMapper.readValue(json, ClassSession.class)
                    : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveClassSessionById(String idClassSession, ClassSession classSession) throws JsonProcessingException {
        String key = "classSession:" + idClassSession;
        var ttl = cacheTtlConfig.getOneMonthSeconds();
        try {
            String json = redisObjectMapper.writeValueAsString(classSession);
            redisTemplate.opsForValue().set(key, json, ttl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
