package com.dat.backend_version_2.domain.tournament;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "BracketNode", schema = "tournament")
public class BracketNode {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idBracket;

    @Column(name = "parent_node_id")
    private Integer parentNodeId;     // Node cha

    @Column(name = "child_node_id")
    private Integer childNodeId;      // Node con (node mà nó trỏ tới)

    private Integer levelNode;

    @ElementCollection
    @CollectionTable(
            name = "bracket_node_links",
            joinColumns = @JoinColumn(name = "bracket_node_id"),
            schema = "association"
    )
    @JsonIgnore
    @Column(name = "linked_node_id")
    private List<Integer> bracketNodes;   // Danh sách các node liên kết trong bracket

    @Column(name = "participants")
    private Integer participants;         // Tổng số người tham gia giải đấu
}
