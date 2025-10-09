package com.dat.backend_version_2.domain.tournament.Poomsae;

import com.dat.backend_version_2.domain.achievement.PoomsaeList;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "PoomsaeHistory", schema = "tournament")
public class PoomsaeHistory {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idPoomsaeHistory;

    @ManyToOne
    @JoinColumn(name = "poomsae_list")
    @JsonIgnore
    private PoomsaeList poomsaeList;

    private Integer sourceNode;       // node gốc
    private Integer targetNode;       // node mục tiêu
    private Integer levelNode;
    private Boolean hasWon;
}
