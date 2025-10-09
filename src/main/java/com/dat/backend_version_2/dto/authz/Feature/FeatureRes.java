package com.dat.backend_version_2.dto.authz.Feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureRes {
    private UUID idFeature;
    private BasicInfo basicInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasicInfo {
        private String featureGroup;
        private String featureName;
        private String iconComponent;
        private Boolean enabled;
        private List<String> roles;
    }
}
