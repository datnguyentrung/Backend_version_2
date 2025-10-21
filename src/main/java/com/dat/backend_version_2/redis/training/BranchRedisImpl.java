package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.domain.training.Branch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchRedisImpl implements BranchRedis {
    private final RedisTemplate<String, Object> redisTemplate;

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
    public List<Branch> getAllBranches() throws JsonProcessingException {
        String key = "branches:all";
        String json = (String) redisTemplate.opsForValue().get(key);
        try {
            return json != null ?
                    redisObjectMapper.readValue(json, redisObjectMapper.getTypeFactory().constructCollectionType(List.class, Branch.class))
                    : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAllBranches(List<Branch> branches) throws JsonProcessingException {
        String key = "branches:all";
        try {
            String json = redisObjectMapper.writeValueAsString(branches);
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
