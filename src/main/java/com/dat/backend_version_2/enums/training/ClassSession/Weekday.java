package com.dat.backend_version_2.enums.training.ClassSession;

import lombok.Getter;

@Getter
public enum Weekday {
    SUNDAY(1),
    MONDAY(2),
    TUESDAY(3),
    WEDNESDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7);

    private final int value;

    Weekday(int value) {
        this.value = value;
    }
}
