package com.dat.backend_version_2.dto.tournament;

import com.dat.backend_version_2.enums.tournament.PoomsaeMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class PoomsaeCombinationDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangePoomsaeModeRequest {
        private List<String> idPoomsaeCombinations;
        private PoomsaeMode poomsaeMode;
    }
}
