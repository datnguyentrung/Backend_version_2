package com.dat.backend_version_2.dto.tournament;

import com.dat.backend_version_2.enums.tournament.TournamentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TournamentDTO {
    private String idTournament;

    @Data
    public static class TournamentInfo {
        private String tournamentName;
        private LocalDate tournamentDate;
        private String location;
        private TournamentStatus tournamentStatus;
    }
}
