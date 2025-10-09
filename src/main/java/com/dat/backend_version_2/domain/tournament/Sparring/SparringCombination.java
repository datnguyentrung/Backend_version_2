package com.dat.backend_version_2.domain.tournament.Sparring;

import com.dat.backend_version_2.domain.tournament.AgeGroup;
import com.dat.backend_version_2.enums.training.Gender;
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
@Table(name = "SparringCombination", schema = "tournament")
public class SparringCombination {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idSparringCombination;

    @ManyToOne
    @JoinColumn(name = "sparring_content")
    private SparringContent sparringContent;

    @ManyToOne
    @JoinColumn(name = "age_group")
    private AgeGroup ageGroup;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean isActive = true;
}
