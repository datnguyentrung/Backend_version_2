package com.dat.backend_version_2.dto.tournament;

import com.dat.backend_version_2.enums.tournament.AgeDivision;
import lombok.Data;

import java.util.List;

@Data
public class AgeGroupDTO {
    private String ageGroupName;
    private List<AgeDivision> ageDivisions;
}
