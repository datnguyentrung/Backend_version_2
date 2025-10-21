package com.dat.backend_version_2.dto.tournament;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentMatchId implements Serializable {
    private UUID tournament;     // trùng với @JoinColumn(name = "tournament")
    private UUID idCombination;
    private Integer targetNode;
}
