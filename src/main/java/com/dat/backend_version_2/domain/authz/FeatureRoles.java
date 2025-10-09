package com.dat.backend_version_2.domain.authz;

import com.dat.backend_version_2.dto.authz.FeatureRolesId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FeatureRoles",
        schema = "authz")
@IdClass(FeatureRolesId.class)
public class FeatureRoles {
    @Id
    @Column(name = "idFeature")
    private UUID idFeature;

    @Id
    @Column(name = "idRole")
    private String idRole;

    @ManyToOne
    @MapsId("idFeature")
    @JoinColumn(name = "idFeature", referencedColumnName = "idFeature")
    @JsonIgnore
    private Feature feature;

    @ManyToOne
    @MapsId("idRole")
    @JoinColumn(name = "idRole", referencedColumnName = "idRole")
    @JsonIgnore
    private Roles role;
}
