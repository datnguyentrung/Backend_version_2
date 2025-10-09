package com.dat.backend_version_2.mapper.training;

import com.dat.backend_version_2.domain.training.ClassSession;
import com.dat.backend_version_2.dto.training.ClassSession.ClassSessionRes;

public class ClassSessionMapper {
    public static ClassSessionRes classSessionToClassSessionRes(ClassSession classSession) {
        if (classSession == null) {
            return null;
        }
        ClassSessionRes classSessionRes = new ClassSessionRes();
        classSessionRes.setIdClassSession(classSession.getIdClassSession());
        classSessionRes.setClassLevel(classSession.getClassLevel());
        classSessionRes.setLocation(classSession.getLocation());
        classSessionRes.setShift(classSession.getShift());
        classSessionRes.setWeekday(classSession.getWeekday());
        classSessionRes.setSession(classSession.getSession());
        classSessionRes.setIsActive(classSession.getIsActive());
        classSessionRes.setIdBranch(classSession.getBranch().getIdBranch());

        return classSessionRes;
    }
}
