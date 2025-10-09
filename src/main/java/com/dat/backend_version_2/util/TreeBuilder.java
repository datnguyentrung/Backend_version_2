package com.dat.backend_version_2.util;

import com.dat.backend_version_2.dto.tournament.BracketNodeReq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeBuilder {
    private static final Map<Integer, BracketNodeReq.Node> nodeMap = new HashMap<>();
    private int counter = 0;

    public BracketNodeReq.Node build(List<Integer> list, Integer parentId, int level) {
        BracketNodeReq.Node node = new BracketNodeReq.Node(counter++, list);
        node.setParentNodeId(parentId);
        node.setLevelNode(level);
        nodeMap.put(node.getChildNodeId(), node);

        if (list.size() > 1) {
            int half = list.size() / 2;
            node.setLeft(build(
                    list.subList(0, half),
                    node.getChildNodeId(),
                    level + 1
            ));
            node.getLeft().setParent(node);

            node.setRight(build(
                    list.subList(half, list.size()),
                    node.getChildNodeId(),
                    level + 1
            ));
            node.getRight().setParent(node);
        } else {
            node.setData(list.getFirst());
        }
        return node;
    }


}
