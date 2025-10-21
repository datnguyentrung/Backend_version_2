package com.dat.backend_version_2.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@Getter
public class CacheTtlConfig {

    @Value("${cache.ttl.one-day-seconds}")
    private long oneDaySeconds;

    @Value("${cache.ttl.one-week-seconds}")
    private long oneWeekSeconds;

    @Value("${cache.ttl.one-month-seconds}")
    private long oneMonthSeconds;

    public Duration randomOneDay() {
        // 1 ngày + random 0-1h
        long extra = ThreadLocalRandom.current().nextLong(0, 3600);
        return Duration.ofSeconds(oneDaySeconds + extra);
    }

    public Duration randomOneWeek() {
        // 1 tuần + random 0-6h
        long extra = ThreadLocalRandom.current().nextLong(0, 21600);
        return Duration.ofSeconds(oneWeekSeconds + extra);
    }

    public Duration randomOneMonth() {
        // 1 tháng + random 0-1 ngày
        long extra = ThreadLocalRandom.current().nextLong(0, 86400);
        return Duration.ofSeconds(oneMonthSeconds + extra);
    }
}
