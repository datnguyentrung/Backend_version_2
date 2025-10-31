package com.dat.backend_version_2.repository.tournament;

import com.dat.backend_version_2.domain.tournament.BracketNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BracketNodeRepository extends JpaRepository<BracketNode, UUID> {
    @Query("""
            SELECT DISTINCT bn
                        FROM BracketNode bn
                                    JOIN FETCH bn.bracketNodes
                                                WHERE bn.participants = :participants
            """)
    List<BracketNode> findByParticipants(@Param("participants") Integer participants);

    BracketNode findByParticipantsAndChildNodeId(Integer participants, Integer childNodeId);

    List<BracketNode> findByParticipantsAndParentNodeId(Integer participants, Integer parentNodeId);
}
