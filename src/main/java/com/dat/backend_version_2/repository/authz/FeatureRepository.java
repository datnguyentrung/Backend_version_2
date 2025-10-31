package com.dat.backend_version_2.repository.authz;

import com.dat.backend_version_2.domain.authz.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, UUID> {

    @Query("""
            SELECT DISTINCT f FROM Feature f
                        JOIN FETCH f.featureRoles fr
                        JOIN FETCH fr.role r
            """)
    List<Feature> findAllWithRoles();
}
