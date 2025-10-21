package com.dat.backend_version_2.redis.authz;

import com.dat.backend_version_2.dto.authz.Feature.FeatureRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureRedisImpl implements FeatureRedis {
    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper redisObjectMapper;

    @Override
    public List<FeatureRes.BasicInfo> getAllFeatures()  {
        String key = "features:all";
        String json = (String) redisTemplate.opsForValue().get(key);
        try {
            return json != null ?
                    redisObjectMapper.readValue(json, redisObjectMapper.getTypeFactory().constructCollectionType(List.class, FeatureRes.BasicInfo.class))
                    : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear(){
        var connectionFactory = redisTemplate.getConnectionFactory();
        if (connectionFactory != null) {
            try (var connection = connectionFactory.getConnection()) {
                connection.serverCommands().flushDb(); // chỉ xóa DB hiện tại
            }
        }
    }

    @Override
    // Save all features to Redis
    public void saveAllFeatures(List<FeatureRes.BasicInfo> features)  {
        String key = "features:all";
        try {
            String json = redisObjectMapper.writeValueAsString(features);
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
