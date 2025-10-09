package com.dat.backend_version_2.dto.authz.Feature;

import lombok.Data;

import java.util.List;

@Data
public class FeatureReq {
    private List<String> roles;
    private FeatureInfo featureInfo;

    @Data
    public static class FeatureInfo{
        private String featureGroup;
        private String featureName;
        private String iconComponent;
    }
}
