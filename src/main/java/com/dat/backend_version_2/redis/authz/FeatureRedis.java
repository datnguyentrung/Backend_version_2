package com.dat.backend_version_2.redis.authz;

import com.dat.backend_version_2.dto.authz.Feature.FeatureRes;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface FeatureRedis {
    void clear();

    List<FeatureRes> getAllEnabledFeatures() throws JsonProcessingException;

    void saveAllEnabledFeatures(List<FeatureRes> features
    ) throws JsonProcessingException;
}
