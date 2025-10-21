package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.config.CacheTtlConfig;
import com.dat.backend_version_2.dto.training.Student.StudentRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
        String json = (String) redisTemplate.opsForValue().get(key);
        return json != null ?
                redisObjectMapper.readValue(json, new TypeReference<List<StudentRes.PersonalAcademicInfo>>() {})
                : null;
    }

    @Override
    public List<StudentRes.PersonalAcademicInfo> getStudentsByIdClassSession(
            String idClassSession
    ) throws JsonProcessingException{
        String key = getKeyClassSession(idClassSession);
        String json = (String) redisTemplate.opsForValue().get(key);
        return json != null ?
                redisObjectMapper.readValue(json, new TypeReference<List<StudentRes.PersonalAcademicInfo>>() {})
                : null;
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
    // Save all students to Redis
    public void saveAllStudents(List<StudentRes.PersonalAcademicInfo> students) throws JsonProcessingException {
        String key = "students:all";
        String json = redisObjectMapper.writeValueAsString(students);
        redisTemplate.opsForValue().set(key, json);
    }

    @Override
    public void saveStudentsByIdClassSession(
            String idClassSession,
            List<StudentRes.PersonalAcademicInfo> students
    ) throws JsonProcessingException {
        String key = getKeyClassSession(idClassSession);
        String json = redisObjectMapper.writeValueAsString(students);

        // Lưu kèm TTL (ví dụ: 1 ngày + random)
        redisTemplate.opsForValue().set(key, json, cacheTtlConfig.randomOneDay());
    }
}
