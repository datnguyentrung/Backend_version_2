package com.dat.backend_version_2.dto.training.Branch;

import com.dat.backend_version_2.enums.training.ClassSession.Weekday;
import lombok.Data;

import java.util.List;

@Data
public class BranchReq {
    private Integer idBranch;
    private BranchInfo branchInfo;

    @Data
    public static class BranchInfo{
        private BasicInfo basic;      // Thông tin cơ bản
        private LocationInfo location; // Địa điểm
    }

    @Data
    public static class BasicInfo {
        private String title;
        private String avatar;
        private Boolean isNew;
    }

    @Data
    public static class LocationInfo {
        private String address;
        private List<Weekday> weekdays;
    }
}