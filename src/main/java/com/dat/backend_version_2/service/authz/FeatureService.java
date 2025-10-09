package com.dat.backend_version_2.service.authz;

import com.dat.backend_version_2.domain.authz.Feature;
import com.dat.backend_version_2.domain.authz.FeatureRoles;
import com.dat.backend_version_2.domain.authz.Roles;
import com.dat.backend_version_2.dto.authz.Feature.FeatureReq;
import com.dat.backend_version_2.dto.authz.Feature.FeatureRes;
import com.dat.backend_version_2.mapper.authz.FeatureMapper;
import com.dat.backend_version_2.repository.authz.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeatureService {
    private final FeatureRepository featureRepository;
    private final RolesService rolesService;

    public Feature createFeature(FeatureReq featureReq) {
        Feature feature = new Feature();
        // Chuyển List<Roles> thành List<FeatureRoles>
        if (featureReq.getRoles() != null) {
            List<FeatureRoles> featureRoles = featureReq.getRoles().stream()
                    .map(idRole -> {
                        Roles role = rolesService.getRoleById(idRole);

                        FeatureRoles fr = new FeatureRoles();
                        fr.setRole(role);
                        fr.setFeature(feature); // liên kết ngược
                        return fr;
                    })
                    .collect(Collectors.toList());
            feature.setFeatureRoles(featureRoles);
        }

        // Map thông tin feature cơ bản
        FeatureMapper.featureInfoToFeature(featureReq.getFeatureInfo(), feature);
        return featureRepository.save(feature);
    }

    public List<FeatureRes.BasicInfo> getAllFeatures() {
        return featureRepository.findAllWithRoles().stream()
                .map(FeatureMapper::featureToBasicInfo)
                .toList();
    }
}
