package com.dat.backend_version_2.domain.tournament;

import com.dat.backend_version_2.enums.tournament.AgeDivision;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AgeGroup", schema = "tournament")
public class AgeGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgeGroup;

    private String ageGroupName;
    private Boolean isActive = true;

    @ElementCollection(targetClass = AgeDivision.class)
    @CollectionTable(
            name = "age_group_age_divisions",
            joinColumns = @JoinColumn(name = "id_age_group")
    )
    @Enumerated(EnumType.STRING)
//    @JsonIgnore
    @Column(name = "age_divisions")
    private List<AgeDivision> ageDivisions;   // Danh sách các node liên kết trong bracket
}
