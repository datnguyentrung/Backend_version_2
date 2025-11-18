package com.dat.backend_version_2.mapper.authz;

import com.dat.backend_version_2.domain.authz.Feature;
import com.dat.backend_version_2.domain.authz.FeatureRoles;
import com.dat.backend_version_2.dto.authz.Feature.FeatureReq;
import com.dat.backend_version_2.dto.authz.Feature.FeatureRes;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FeatureMapper {
    public static void featureInfoToFeature(
            FeatureReq.FeatureInfo featureInfo, Feature feature) {
        feature.setFeatureGroup(featureInfo.getFeatureGroup());
        feature.setFeatureName(featureInfo.getFeatureName());
        feature.setIconComponent(featureInfo.getIconComponent());
    }

    public static FeatureRes.BasicInfo featureToBasicInfo(Feature feature) {
        if (feature == null) {
            return null;
        }
        FeatureRes.BasicInfo basicInfo = new FeatureRes.BasicInfo();
        basicInfo.setFeatureName(feature.getFeatureName());
        basicInfo.setFeatureGroup(feature.getFeatureGroup());
        basicInfo.setIconComponent(feature.getIconComponent());
        basicInfo.setEnabled(feature.getEnabled());

        if (feature.getFeatureRoles() != null) {
            basicInfo.setRoles(
                    feature.getFeatureRoles().stream()
                            .map(FeatureRoles::getIdRole) // lấy roleName hoặc idRole
                            .collect(Collectors.toList())
            );
        } else {
            basicInfo.setRoles(new ArrayList<>());
        }

        return basicInfo;
    }

    public static FeatureRes featureToFeatureRes(Feature feature) {
        if (feature == null) {
            return null;
        }
        FeatureRes featureRes = new FeatureRes();
        featureRes.setIdFeature(feature.getIdFeature());
        featureRes.setBasicInfo(featureToBasicInfo(feature));
        return featureRes;
    }
}
