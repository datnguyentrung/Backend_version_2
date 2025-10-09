package com.dat.backend_version_2.dto.tournament;

import com.dat.backend_version_2.enums.training.BeltLevel;
import lombok.Data;

import java.util.List;

@Data
public class BeltGroupDTO {
    private String beltGroupName;
    private List<BeltLevel> beltLevels;
}
