package com.dat.backend_version_2.mapper.tournament;

import com.dat.backend_version_2.domain.tournament.TournamentMatch;
import com.dat.backend_version_2.dto.tournament.TournamentMatchDTO;

public class TournamentMatchMapper {
    public static TournamentMatchDTO.KeyInfo tournamentMatchToKeyInfo(TournamentMatch tournamentMatch) {
        if (tournamentMatch == null) return null;
        TournamentMatchDTO.KeyInfo keyInfo = new TournamentMatchDTO.KeyInfo();
        keyInfo.setTournament(tournamentMatch.getTournament().getIdTournament().toString());
        keyInfo.setIdCombination(tournamentMatch.getIdCombination().toString());
        keyInfo.setTargetNode(tournamentMatch.getTargetNode());
        keyInfo.setParticipants(tournamentMatch.getParticipants());
        keyInfo.setFirstNode(tournamentMatch.isFirstNode());
        return keyInfo;
    }

    public static TournamentMatchDTO.MatchInfo tournamentMatchToMatchInfo(TournamentMatch tournamentMatch) {
        if (tournamentMatch == null) return null;
        TournamentMatchDTO.MatchInfo matchInfo = new TournamentMatchDTO.MatchInfo();
        matchInfo.setTournamentType(tournamentMatch.getTournamentType());
        matchInfo.setDuration(tournamentMatch.getDuration());
        matchInfo.setSession(tournamentMatch.getSession());
        return matchInfo;
    }

    public static TournamentMatchDTO.RelationInfo tournamentMatchToRelationInfo(TournamentMatch tournamentMatch) {
        if (tournamentMatch == null) return null;
        TournamentMatchDTO.RelationInfo relationInfo = new TournamentMatchDTO.RelationInfo();
        relationInfo.setLeftMatch(tournamentMatchToKeyInfo(tournamentMatch.getLeftMatch()));
        relationInfo.setRightMatch(tournamentMatchToKeyInfo(tournamentMatch.getRightMatch()));
        return relationInfo;
    }

    public static TournamentMatchDTO tournamentMatchToDTO(TournamentMatch tournamentMatch) {
        if (tournamentMatch == null) return null;
        TournamentMatchDTO dto = new TournamentMatchDTO();
        dto.setKeyInfo(tournamentMatchToKeyInfo(tournamentMatch));
        dto.setMatchInfo(tournamentMatchToMatchInfo(tournamentMatch));
        dto.setRelationInfo(tournamentMatchToRelationInfo(tournamentMatch));
        return dto;
    }
}
