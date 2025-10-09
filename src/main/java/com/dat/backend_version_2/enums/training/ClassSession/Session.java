package com.dat.backend_version_2.enums.training.ClassSession;

import lombok.Getter;

@Getter
public enum Session {
    AM("A"),
    PM("P");

    private final String session;

    Session(String session) {
        this.session = session;
    }
}
