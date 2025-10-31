package com.dat.backend_version_2.controller.authz;

import com.dat.backend_version_2.domain.authz.Feature;
import com.dat.backend_version_2.dto.authz.Feature.FeatureReq;
import com.dat.backend_version_2.dto.authz.Feature.FeatureRes;
import com.dat.backend_version_2.mapper.authz.FeatureMapper;
import com.dat.backend_version_2.redis.authz.FeatureRedisImpl;
import com.dat.backend_version_2.service.authz.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/features")
@RequiredArgsConstructor
public class FeatureController {
    private final FeatureService featureService;

    private final FeatureRedisImpl featureRedis;

    @PostMapping
    public ResponseEntity<FeatureRes.BasicInfo> createFeature(
            @RequestBody FeatureReq featureInfo) {
        Feature feature = featureService.createFeature(featureInfo);
        FeatureRes.BasicInfo basicInfo = FeatureMapper.featureToBasicInfo(feature);

        return ResponseEntity.status(HttpStatus.CREATED).body(basicInfo);
    }

    @GetMapping
    public ResponseEntity<List<FeatureRes.BasicInfo>> getAllFeatures() {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(featureService.getAllFeatures());
        List<FeatureRes.BasicInfo> features = featureRedis.getAllFeatures();
        if (features == null) {
            features = featureService.getAllFeatures();
            featureRedis.saveAllFeatures(features);
        }
        return ResponseEntity.ok(features);
    }
}
