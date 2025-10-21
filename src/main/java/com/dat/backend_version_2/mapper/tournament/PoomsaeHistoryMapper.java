package com.dat.backend_version_2.mapper.tournament;

import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeHistory;
import com.dat.backend_version_2.dto.tournament.PoomsaeHistoryDTO;

public class PoomsaeHistoryMapper {

    public static PoomsaeHistoryDTO poomsaeHistoryToPoomsaeHistoryDTO(PoomsaeHistory poomsaeHistory) {
        if (poomsaeHistory == null) return null;
        PoomsaeHistoryDTO dto = new PoomsaeHistoryDTO();
        dto.setIdPoomsaeHistory(String.valueOf(poomsaeHistory.getIdPoomsaeHistory()));
        dto.setReferenceInfo(poomsaeHistoryToReferenceInfo(poomsaeHistory));
        dto.setNodeInfo(poomsaeHistoryToNodeInfo(poomsaeHistory));
        dto.setHasWon(poomsaeHistory.getHasWon());

        return dto;
    }

    private static PoomsaeHistoryDTO.ReferenceInfo poomsaeHistoryToReferenceInfo(PoomsaeHistory poomsaeHistory) {
        if (poomsaeHistory == null) return null;

        var referenceInfo = new PoomsaeHistoryDTO.ReferenceInfo();
        var poomsaeList = poomsaeHistory.getPoomsaeList();

        if (poomsaeList != null) {
            var student = poomsaeList.getStudent();
            var combination = poomsaeList.getPoomsaeCombination();

            referenceInfo.setName(student != null ? student.getName() : null);
            referenceInfo.setPoomsaeList(String.valueOf(poomsaeList.getIdPoomsaeList()));
            referenceInfo.setPoomsaeCombination(
                    combination != null ? String.valueOf(combination.getIdPoomsaeCombination()) : null
            );
            referenceInfo.setPoomsaeCategory(poomsaeHistoryToPoomsaeCategory(poomsaeHistory));
        }

        return referenceInfo;
    }

    public static PoomsaeHistoryDTO.PoomsaeCategory poomsaeHistoryToPoomsaeCategory(PoomsaeHistory poomsaeHistory) {
        if (poomsaeHistory == null) return null;
        var poomsaeCategory = new PoomsaeHistoryDTO.PoomsaeCategory();
        var poomsaeList = poomsaeHistory.getPoomsaeList();
        if (poomsaeList != null) {
            var combination = poomsaeList.getPoomsaeCombination();
            poomsaeCategory.setAgeGroupName(combination.getAgeGroup().getAgeGroupName());
            poomsaeCategory.setBeltGroupName(combination.getBeltGroup().getBeltGroupName());
            poomsaeCategory.setContentName(combination.getPoomsaeContent().getContentName());
        }
        return poomsaeCategory;
    }

    public static PoomsaeHistoryDTO.NodeInfo poomsaeHistoryToNodeInfo(PoomsaeHistory poomsaeHistory) {
        if (poomsaeHistory == null) return null;
        PoomsaeHistoryDTO.NodeInfo nodeInfo = new PoomsaeHistoryDTO.NodeInfo();
        nodeInfo.setTargetNode(poomsaeHistory.getTargetNode());
        nodeInfo.setSourceNode(poomsaeHistory.getSourceNode());
        nodeInfo.setLevelNode(poomsaeHistory.getLevelNode());
        return nodeInfo;
    }
}
