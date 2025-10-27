package com.dat.backend_version_2.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class ConverterUtils {
    public static LocalDate instantToLocalDate(Instant instant) {
        return instant.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate();
    }
}
