package com.dat.backend_version_2.mapper.tournament;

import com.dat.backend_version_2.domain.tournament.Sparring.SparringHistory;
import com.dat.backend_version_2.dto.tournament.SparringHistoryDTO;

public class SparringHistoryMapper {
    public static SparringHistoryDTO sparringHistoryToSparringHistoryDTO(SparringHistory sparringHistory) {
        if (sparringHistory == null) return null;
        SparringHistoryDTO dto = new SparringHistoryDTO();
        dto.setIdSparringHistory(String.valueOf(sparringHistory.getIdSparringHistory()));
        dto.setReferenceInfo(sparringHistoryToReferenceInfo(sparringHistory));
        dto.setNodeInfo(sparringHistoryToNodeInfo(sparringHistory));
        dto.setHasWon(sparringHistory.getHasWon());

        return dto;
    }

    private static SparringHistoryDTO.ReferenceInfo sparringHistoryToReferenceInfo(SparringHistory sparringHistory) {
        if (sparringHistory == null) return null;

        var referenceInfo = new SparringHistoryDTO.ReferenceInfo();
        var sparringList = sparringHistory.getSparringList();

        if (sparringList != null) {
            var student = sparringList.getStudent();
            var combination = sparringList.getSparringCombination();

            referenceInfo.setName(student != null ? student.getName() : null);
            referenceInfo.setSparringList(String.valueOf(sparringList.getIdSparringList()));
            referenceInfo.setSparringCombination(
                    combination != null ? String.valueOf(combination.getIdSparringCombination()) : null
            );
        }

        return referenceInfo;
    }

    private static SparringHistoryDTO.NodeInfo sparringHistoryToNodeInfo(SparringHistory sparringHistory) {
        if (sparringHistory == null) return null;
        SparringHistoryDTO.NodeInfo nodeInfo = new SparringHistoryDTO.NodeInfo();
        nodeInfo.setTargetNode(sparringHistory.getTargetNode());
        nodeInfo.setSourceNode(sparringHistory.getSourceNode());
        nodeInfo.setLevelNode(sparringHistory.getLevelNode());
        return nodeInfo;
    }
}
