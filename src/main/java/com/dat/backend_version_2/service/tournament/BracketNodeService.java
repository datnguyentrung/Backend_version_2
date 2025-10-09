package com.dat.backend_version_2.service.tournament;

import com.dat.backend_version_2.domain.tournament.BracketNode;
import com.dat.backend_version_2.dto.tournament.BracketNodeReq;
import com.dat.backend_version_2.mapper.tournament.BracketNodeMapper;
import com.dat.backend_version_2.repository.tournament.BracketNodeRepository;
import com.dat.backend_version_2.util.TreeBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BracketNodeService {
    private final BracketNodeRepository bracketNodeRepository;
    private final BracketNodeTransactionalService bracketNodeTransactionalService;

    // Thuật toán tạo node phù hợp với Participants
    public List<BracketNodeReq.BracketInfo> createListNode(Integer participants) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < participants; i++) list.add(i);

        TreeBuilder treeBuilder = new TreeBuilder();
        BracketNodeReq.Node root = treeBuilder.build(list, null, 0);

        List<BracketNodeReq.BracketInfo> bracketInfos = new ArrayList<>();
        traverseTree(root, 0, bracketInfos, participants);
        return bracketInfos;
    }

    private void traverseTree(
            BracketNodeReq.Node root,
            int level,
            List<BracketNodeReq.BracketInfo> bracketInfos,
            Integer participants) {
        if (root == null) return;
        System.out.println("  ".repeat(level) + "Node " + root.getChildNodeId() +
                ": " + root.getBracketNodes() +
                "- " + root.getParentNodeId() +
                " Level " + root.getLevelNode());
        bracketInfos.add(new BracketNodeReq.BracketInfo(
                root.getParentNodeId(),
                root.getChildNodeId(),
                root.getLevelNode(),
                root.getBracketNodes(),
                participants
        ));

        traverseTree(root.getLeft(), level + 1, bracketInfos, participants);
        traverseTree(root.getRight(), level + 1, bracketInfos, participants);
    }

    @Transactional
    public void createBracketNodeAllowParticipants(Integer participants) {
        if (participants == null || participants == 0) return;

        List<BracketNodeReq.BracketInfo> bracketInfos = createListNode(participants);
        List<BracketNode> bracketNodes = BracketNodeMapper.BracketNodeInfoListToSigmaList(bracketInfos);

        bracketNodeRepository.saveAll(bracketNodes);
    }

    public List<BracketNodeReq.BracketInfo> getBracketNodeByParticipants(Integer participants) {
        List<BracketNodeReq.BracketInfo> bracketInfos = bracketNodeRepository.findByParticipants(participants)
                .stream()
                .map(BracketNodeMapper::BracketNodeToBracketInfo)
                .toList();

        // Nếu chưa có node, tạo mới
        if (bracketInfos.isEmpty()) {
            List<BracketNodeReq.BracketInfo> newInfos = createListNode(participants);
            List<BracketNode> bracketNodes = BracketNodeMapper.BracketNodeInfoListToSigmaList(newInfos);

            // Gọi service transactional
            bracketNodeTransactionalService.createBracketNodeAllowParticipants(bracketNodes);

            return newInfos;
        }
        return bracketInfos;
    }

    public BracketNode getBracketNodeByParticipantsAndChildNodeId(Integer participants, Integer childNodeId) {
        return bracketNodeRepository.findByParticipantsAndChildNodeId(participants, childNodeId);
    }

    public List<BracketNode> getBracketNodeByParticipantsAndParentNodeId(Integer participants, Integer parentNodeId) {
        return bracketNodeRepository.findByParticipantsAndParentNodeId(participants, parentNodeId);
    }
}
