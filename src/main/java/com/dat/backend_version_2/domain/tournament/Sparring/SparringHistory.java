package com.dat.backend_version_2.domain.tournament.Sparring;

import com.dat.backend_version_2.domain.achievement.SparringList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "SparringHistory", schema = "tournament")
public class SparringHistory {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idSparringHistory;

    @ManyToOne
    @JoinColumn(name = "sparring_list_id_sparring_list")
    private SparringList sparringList;

    private Integer sourceNode;       // node gốc
    private Integer targetNode;       // node mục tiêu
    private Integer levelNode;
    private Boolean hasWon;
}
