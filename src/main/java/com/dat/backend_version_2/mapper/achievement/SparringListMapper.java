package com.dat.backend_version_2.mapper.achievement;

import com.dat.backend_version_2.domain.achievement.SparringList;
import com.dat.backend_version_2.dto.achievement.SparringListDTO;
import com.dat.backend_version_2.mapper.training.StudentMapper;

public class SparringListMapper {
    public static SparringListDTO sparringListToDTO(SparringList entity) {
        return new SparringListDTO(
                entity.getIdSparringList().toString(),
                sparringListToCompetitorDTO(entity)
        );
    }

    public static SparringListDTO.CompetitorDTO sparringListToCompetitorDTO(SparringList sparringList) {
        if (sparringList == null) return null;
        SparringListDTO.CompetitorDTO competitorDTO = new SparringListDTO.CompetitorDTO();
        competitorDTO.setCompetition(sparringListToCompetitionDTO(sparringList));
        competitorDTO.setMedal(sparringList.getMedal());
        competitorDTO.setPersonalAcademicInfo(
                StudentMapper.studentToPersonalAcademicInfo(sparringList.getStudent())
        );
        return competitorDTO;
    }

    public static SparringListDTO.CompetitionDTO sparringListToCompetitionDTO(SparringList sparringList) {
        if (sparringList == null) return null;
        SparringListDTO.CompetitionDTO competitionDTO = new SparringListDTO.CompetitionDTO();
        competitionDTO.setIdSparringCombination(sparringList.getSparringCombination().getIdSparringCombination().toString());
        competitionDTO.setIdTournament(sparringList.getTournament().getIdTournament().toString());
        return competitionDTO;
    }
}

