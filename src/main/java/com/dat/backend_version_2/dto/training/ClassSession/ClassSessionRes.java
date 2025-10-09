package com.dat.backend_version_2.dto.training.ClassSession;

import com.dat.backend_version_2.enums.training.ClassSession.*;
import lombok.Data;

@Data
public class ClassSessionRes {
    private String idClassSession;
    private ClassLevel classLevel;
    private Location location;
    private Shift shift;
    private Weekday weekday;
    private Session session;
    private Boolean isActive;
    private Integer idBranch;
}
