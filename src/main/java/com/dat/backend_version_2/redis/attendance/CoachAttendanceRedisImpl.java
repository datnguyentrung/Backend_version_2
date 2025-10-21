package com.dat.backend_version_2.redis.attendance;

import com.dat.backend_version_2.config.CacheTtlConfig;
import com.dat.backend_version_2.dto.attendance.CoachAttendanceRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachAttendanceRedisImpl implements CoachAttendanceRedis{
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    private final CacheTtlConfig cacheTtlConfig;

    private String getKeyByIdCoachAndYearAndMonth(
            String idCoach,
            Integer year,
            Integer month
    ) {
        return "coach-attendance:" + idCoach + ":" + year + ":" + month;
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
    public List<CoachAttendanceRes> getCoachAttendanceByIdCoachAndYearAndMonth(
            String idCoach,
            Integer year,
            Integer month
    ) throws JsonProcessingException{
        String key = getKeyByIdCoachAndYearAndMonth(idCoach, year, month);
        String jsonData = (String) redisTemplate.opsForValue().get(key);
        if (jsonData != null) {
            return List.of(redisObjectMapper.readValue(
                    jsonData,
                    CoachAttendanceRes[].class
            ));
        }
        return null;
    }

    @Override
    public void saveCoachAttendanceByIdCoachAndYearAndMonth(
            String idCoach,
            Integer year,
            Integer month,
            List<CoachAttendanceRes> coachAttendanceResList
    ) throws JsonProcessingException{
        String key = getKeyByIdCoachAndYearAndMonth(idCoach, year, month);
        String jsonData = redisObjectMapper.writeValueAsString(coachAttendanceResList);
        redisTemplate.opsForValue().set(key, jsonData, cacheTtlConfig.randomOneMonth());
    }
}
