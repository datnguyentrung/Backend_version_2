package com.dat.backend_version_2.repository.tournament.Poomsae;

import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeCombination;
import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PoomsaeHistoryRepository extends JpaRepository<PoomsaeHistory, UUID> {
    @Query("""
                SELECT DISTINCT ph
                FROM PoomsaeHistory ph
                    JOIN FETCH ph.poomsaeList pl
                    JOIN FETCH pl.student s
                    JOIN FETCH pl.poomsaeCombination pc
                WHERE pc = :poomsaeCombination
            """)
    List<PoomsaeHistory> findAllByPoomsaeCombination(@Param("poomsaeCombination") PoomsaeCombination poomsaeCombination);

    @Query("""
            SELECT DISTINCT ph
                FROM PoomsaeHistory ph
                    JOIN FETCH ph.poomsaeList pl
                    JOIN FETCH pl.student s
                    JOIN FETCH pl.poomsaeCombination pc
                WHERE pc.idPoomsaeCombination = :idPoomsaeCombination
                  AND ph.targetNode = :targetNode
            """)
    List<PoomsaeHistory> findAllByTargetNodeAndIdPoomsaeCombination(
            @Param("targetNode") Integer targetNode,
            @Param("idPoomsaeCombination") UUID idPoomsaeCombination
    );

    Integer countPoomsaeHistoryByLevelNode(Integer levelNode);
}
