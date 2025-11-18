package com.dat.backend_version_2.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class ConverterUtils {
    public static LocalDate instantToLocalDate(Instant instant) {
        return instant.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate();
    }

    public static LocalDate localDateTimeToLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

    public static List<Integer> getMonthsByQuarter(int quarter) {
        return switch (quarter) {
            case 1 -> List.of(1, 2, 3);
            case 2 -> List.of(4, 5, 6);
            case 3 -> List.of(7, 8, 9);
            case 4 -> List.of(10, 11, 12);
            default -> List.of();
        };
    }
}
