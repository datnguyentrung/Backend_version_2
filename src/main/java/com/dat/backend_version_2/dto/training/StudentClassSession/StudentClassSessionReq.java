package com.dat.backend_version_2.dto.training.StudentClassSession;

import lombok.Data;

import java.util.List;

@Data
public class StudentClassSessionReq {
    private String idUser;
    private List<String> idClassSessions;
}
