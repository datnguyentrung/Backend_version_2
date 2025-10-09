package com.dat.backend_version_2.mapper.tournament;

import com.dat.backend_version_2.domain.tournament.BracketNode;
import com.dat.backend_version_2.dto.tournament.BracketNodeReq;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BracketNodeMapper {
    public static BracketNode BracketInfoToSigma(BracketNodeReq.BracketInfo bracketInfo) {
        if (bracketInfo == null) return null;
        BracketNode bracketNode = new BracketNode();
        bracketNode.setParticipants(bracketInfo.getParticipants());
        bracketNode.setParentNodeId(bracketInfo.getParentNodeId());
        bracketNode.setLevelNode(bracketInfo.getLevelNode());
        bracketNode.setBracketNodes(bracketInfo.getBracketNodes());
        bracketNode.setChildNodeId(bracketInfo.getChildNodeId());

        return bracketNode;
    }

    public static List<BracketNode> BracketNodeInfoListToSigmaList(List<BracketNodeReq.BracketInfo> sigmaInfos) {
        if (sigmaInfos == null) return Collections.emptyList();
        return sigmaInfos.stream()
                .map(BracketNodeMapper::BracketInfoToSigma)
                .collect(Collectors.toList());
    }

    public static BracketNodeReq.BracketInfo BracketNodeToBracketInfo(BracketNode bracketNode) {
        if (bracketNode == null) return null;
        BracketNodeReq.BracketInfo bracketInfo = new BracketNodeReq.BracketInfo();
        bracketInfo.setParticipants(bracketNode.getParticipants());
        bracketInfo.setParentNodeId(bracketNode.getParentNodeId());
        bracketInfo.setLevelNode(bracketNode.getLevelNode());
        bracketInfo.setBracketNodes(bracketNode.getBracketNodes());
        bracketInfo.setChildNodeId(bracketNode.getChildNodeId());
        return bracketInfo;
    }
}
