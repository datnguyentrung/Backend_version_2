package com.dat.backend_version_2.enums.training.ClassSession;

import lombok.Getter;

@Getter
public enum Shift {
    SHIFT_1(1),
    SHIFT_2(2);

    private final int value;

    Shift(int value) {
        this.value = value;
    }
}
