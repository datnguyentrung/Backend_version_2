package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.config.CacheTtlConfig;
import com.dat.backend_version_2.domain.training.Branch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchRedisImpl implements BranchRedis {
    private final StringRedisTemplate stringRedisTemplate;
    private final CacheTtlConfig cacheTtlConfig;
    private final ObjectMapper redisObjectMapper;
    private final RedisConnectionFactory connectionFactory;

    @Override
    public void clear() {
        if (connectionFactory != null) {
            try (var connection = connectionFactory.getConnection()) {
                connection.serverCommands().flushDb(); // chỉ xóa DB hiện tại
                log.info("Redis cache cleared successfully.");
            }
        }
    }

    @Override
    public List<Branch> getAllBranches() throws JsonProcessingException {
        String key = "branches:all";
        String json = stringRedisTemplate.opsForValue().get(key);
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
            stringRedisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Branch getBranchById(int id) throws JsonProcessingException {
        String key = "branch:" + id;
        String json = stringRedisTemplate.opsForValue().get(key);
        try {
            return json != null ? redisObjectMapper.readValue(json, Branch.class) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveBranchById(int idBranch, Branch branch) throws JsonProcessingException {
        String key = "branch:" + idBranch;
        var ttl = cacheTtlConfig.getOneMonthSeconds();
        try {
            String json = redisObjectMapper.writeValueAsString(branch);
            stringRedisTemplate.opsForValue().set(key, json, Duration.ofSeconds(ttl));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
