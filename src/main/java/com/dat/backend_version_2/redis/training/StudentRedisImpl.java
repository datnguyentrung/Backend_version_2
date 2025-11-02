package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.config.CacheTtlConfig;
import com.dat.backend_version_2.dto.training.Student.StudentRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate; // Giữ lại StringRedisTemplate
import org.springframework.data.redis.connection.RedisConnectionFactory; // Cần thiết cho clear()
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentRedisImpl implements StudentRedis {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper redisObjectMapper;
    private final CacheTtlConfig cacheTtlConfig;
    private final RedisConnectionFactory connectionFactory;

    private String getKeyClassSession(String classSessionId) {
        return "students:classSession:" + classSessionId;
    }

    // --- Phương thức ĐỌC (getAllStudents) ---
    @Override
    public List<StudentRes.PersonalAcademicInfo> getAllStudents()
            throws JsonProcessingException {
        String key = "students:all";
        log.debug("Attempting to get all students from cache with key: {}", key);

        // Dùng StringRedisTemplate để đọc String
        String json = stringRedisTemplate.opsForValue().get(key);

        try{
            return json != null ?
                    redisObjectMapper.readValue(json, redisObjectMapper.getTypeFactory().constructCollectionType(List.class, StudentRes.PersonalAcademicInfo.class))
                    : null;
        } catch (Exception e) {
            // Ném exception có ý nghĩa hơn, hoặc log chi tiết hơn
            log.error("Error reading JSON from cache key {}: {}", key, e.getMessage());
            throw new RuntimeException("Error during JSON deserialization from Redis", e);
        }
    }

    // --- Phương thức ĐỌC (getStudentsByIdClassSession) ---
    @Override
    public List<StudentRes.PersonalAcademicInfo> getStudentsByIdClassSession(
            String idClassSession
    ) throws JsonProcessingException{
        String key = getKeyClassSession(idClassSession);
        log.debug("Attempting to get students from cache for class session: {} with key: {}", idClassSession, key);

        // Dùng StringRedisTemplate để kiểm tra key
        Boolean keyExists = stringRedisTemplate.hasKey(key);
        log.debug("Key exists in Redis: {}", keyExists);

        // Dùng StringRedisTemplate để đọc String
        String json = stringRedisTemplate.opsForValue().get(key);

        try {
            return json != null ?
                    redisObjectMapper.readValue(json, redisObjectMapper.getTypeFactory().constructCollectionType(List.class, StudentRes.PersonalAcademicInfo.class))
                    : null;
        } catch (Exception e) {
            log.error("Error reading JSON from cache key {}: {}", key, e.getMessage());
            throw new RuntimeException("Error during JSON deserialization from Redis", e);
        }
    }

    // --- Phương thức XÓA CACHE (clear) ---
    @Override
    public void clear() {
        log.info("Clearing all cache data (FLUSHDB)");

        // Dùng connectionFactory đã inject
        if (connectionFactory != null) {
            try (var connection = connectionFactory.getConnection()) {
                connection.serverCommands().flushDb();
                log.info("Cache cleared successfully");
            }
        }
    }

    // --- Phương thức GHI (saveAllStudents) ---
    @Override
    public void saveAllStudents(List<StudentRes.PersonalAcademicInfo> students) throws JsonProcessingException {
        String key = "students:all";

        try{
            String json = redisObjectMapper.writeValueAsString(students);
            var ttl = cacheTtlConfig.getOneMonthSeconds();

            // Ghi với StringRedisTemplate và TTL
            stringRedisTemplate.opsForValue().set(key, json, Duration.ofSeconds(ttl));
        } catch (Exception e) {
            log.error("Error saving all students to cache", e);
            throw new RuntimeException("Error saving all students to cache", e);
        }

        log.debug("Saved all students to cache with key: {}, count: {}", key, students.size());
    }

    // --- Phương thức GHI (saveStudentsByIdClassSession) ---
    @Override
    public void saveStudentsByIdClassSession(
            String idClassSession,
            List<StudentRes.PersonalAcademicInfo> students
    ) throws JsonProcessingException {
        String key = getKeyClassSession(idClassSession);

        try {
            String json = redisObjectMapper.writeValueAsString(students);

            // Lưu với TTL 1 ngày + random
            var ttl = cacheTtlConfig.randomOneDay();

            // DÙNG stringRedisTemplate thay vì redisTemplate
            stringRedisTemplate.opsForValue().set(key, json, ttl);

            log.info("Saved students to cache for class session: {} with key: {}, count: {}, TTL: {} seconds",
                    idClassSession, key, students.size(), ttl.getSeconds());
        } catch (Exception e) {
            log.error("Error saving students for class session {} to cache", idClassSession, e);
            throw new RuntimeException("Error saving class session students to cache", e);
        }
    }
}