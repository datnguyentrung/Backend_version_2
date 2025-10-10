package com.dat.backend_version_2.domain.tournament;

import com.dat.backend_version_2.enums.tournament.AgeDivision;
import com.dat.backend_version_2.enums.training.BeltLevel;
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
@Table(name = "BeltGroup", schema = "tournament")
public class BeltGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBeltGroup;

    private String beltGroupName;
    private Boolean isActive = true;

    @ElementCollection(targetClass = BeltLevel.class)
    @CollectionTable(
            name = "belt_group_belt_levels",
            joinColumns = @JoinColumn(name = "id_belt_group"),
            schema = "association"
    )
    @Enumerated(EnumType.STRING)
//    @JsonIgnore
    @Column(name = "belt_levels")
    private List<BeltLevel> beltLevels;
}
