package com.dat.backend_version_2.domain.tournament.Poomsae;

import com.dat.backend_version_2.domain.tournament.AgeGroup;
import com.dat.backend_version_2.domain.tournament.BeltGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PoomsaeCombination", schema = "tournament")
public class PoomsaeCombination {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idPoomsaeCombination;

    @ManyToOne
    @JoinColumn(name = "poomsae_content")
    private PoomsaeContent poomsaeContent;

    @ManyToOne
    @JoinColumn(name = "age_group")
    private AgeGroup ageGroup;

    @ManyToOne
    @JoinColumn(name = "belt_group")
    private BeltGroup beltGroup;
}
