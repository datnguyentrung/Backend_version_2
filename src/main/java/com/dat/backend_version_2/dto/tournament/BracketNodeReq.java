package com.dat.backend_version_2.dto.tournament;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class BracketNodeReq {
    private String idBracket; // id của node trong bracket (UUID dạng String)

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BracketInfo {
        private Integer parentNodeId;   // node cha
        private Integer childNodeId;    // node con
        private Integer levelNode;
        private List<Integer> bracketNodes;
        private Integer participants;   // số lượng người tham gia
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Node {
        private Integer parentNodeId;
        private Integer childNodeId;
        private Integer levelNode;
        private List<Integer> bracketNodes;
        private Integer data;
        private boolean hasWon = false;

        private Node parent;
        private Node left;
        private Node right;

        public Node(Integer childNodeId, List<Integer> bracketNodes) { // 👈 đổi lại
            this.childNodeId = childNodeId;
            this.bracketNodes = bracketNodes;
        }

        @Override
        public String toString() {
            return "Node{id=" + childNodeId +
                    ", list=" + bracketNodes +
                    ", parentId=" + parentNodeId +
                    ", level=" + levelNode +
                    ", data=" + data + "}";
        }
    }
}
