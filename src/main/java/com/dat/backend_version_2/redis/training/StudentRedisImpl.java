package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.config.CacheTtlConfig;
import com.dat.backend_version_2.dto.training.Student.StudentRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentRedisImpl implements StudentRedis {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    private final CacheTtlConfig cacheTtlConfig;

    private String getKeyClassSession(String classSessionId) {
        return "students:classSession:" + classSessionId;
    }

    @Override
    public List<StudentRes.PersonalAcademicInfo> getAllStudents()
    throws JsonProcessingException {
        String key = "students:all";
        log.debug("Attempting to get all students from cache with key: {}", key);

        String json = (String) redisTemplate.opsForValue().get(key);
        if (json != null) {
            log.debug("Cache HIT for all students");
            return redisObjectMapper.readValue(json, new TypeReference<List<StudentRes.PersonalAcademicInfo>>() {});
        } else {
            log.debug("Cache MISS for all students");
            return null;
        }
    }

    @Override
    public List<StudentRes.PersonalAcademicInfo> getStudentsByIdClassSession(
            String idClassSession
    ) throws JsonProcessingException{
        String key = getKeyClassSession(idClassSession);
        log.debug("Attempting to get students from cache for class session: {} with key: {}", idClassSession, key);

        // Kiểm tra xem key có tồn tại không
        Boolean keyExists = redisTemplate.hasKey(key);
        log.debug("Key exists in Redis: {}", keyExists);

        String json = (String) redisTemplate.opsForValue().get(key);
        if (json != null) {
            log.info("Cache HIT for class session: {}", idClassSession);
            return redisObjectMapper.readValue(json, new TypeReference<List<StudentRes.PersonalAcademicInfo>>() {});
        } else {
            log.info("Cache MISS for class session: {}", idClassSession);
            return null;
        }
    }

    @Override
    public void clear() {
        log.info("Clearing all cache data");
        var connectionFactory = redisTemplate.getConnectionFactory();
        if (connectionFactory != null) {
            try (var connection = connectionFactory.getConnection()) {
                connection.serverCommands().flushDb();
                log.info("Cache cleared successfully");
            }
        }
    }

    @Override
    public void saveAllStudents(List<StudentRes.PersonalAcademicInfo> students) throws JsonProcessingException {
        String key = "students:all";
        String json = redisObjectMapper.writeValueAsString(students);
        var ttl = cacheTtlConfig.getOneMonthSeconds();
        redisTemplate.opsForValue().set(key, json, ttl);
        log.debug("Saved all students to cache with key: {}, count: {}", key, students.size());
    }

    @Override
    public void saveStudentsByIdClassSession(
            String idClassSession,
            List<StudentRes.PersonalAcademicInfo> students
    ) throws JsonProcessingException {
        String key = getKeyClassSession(idClassSession);
        String json = redisObjectMapper.writeValueAsString(students);

        // Lưu với TTL 1 ngày + random
        var ttl = cacheTtlConfig.randomOneDay();
        redisTemplate.opsForValue().set(key, json, ttl);

        log.info("Saved students to cache for class session: {} with key: {}, count: {}, TTL: {} seconds",
                idClassSession, key, students.size(), ttl.getSeconds());
    }
}
