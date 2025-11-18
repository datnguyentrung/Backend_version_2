package com.dat.backend_version_2.redis.authz;

import com.dat.backend_version_2.dto.authz.Feature.FeatureRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureRedisImpl implements FeatureRedis {
    private final StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper redisObjectMapper;

    @Override
    public List<FeatureRes> getAllEnabledFeatures() {
        String key = "features:all";
        String json = stringRedisTemplate.opsForValue().get(key);
        try {
            return json != null ?
                    redisObjectMapper.readValue(json, redisObjectMapper.getTypeFactory().constructCollectionType(List.class, FeatureRes.class))
                    : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        var connectionFactory = stringRedisTemplate.getConnectionFactory();
        if (connectionFactory != null) {
            try (var connection = connectionFactory.getConnection()) {
                connection.serverCommands().flushDb(); // chỉ xóa DB hiện tại
            }
        }
    }

    @Override
    // Save all features to Redis
    public void saveAllEnabledFeatures(List<FeatureRes> features) throws JsonProcessingException {
        String key = "features:all";
        try {
            String json = redisObjectMapper.writeValueAsString(features);
            stringRedisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
