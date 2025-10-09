package com.dat.backend_version_2.repository.tournament.Sparring;

import com.dat.backend_version_2.domain.tournament.Sparring.SparringCombination;
import com.dat.backend_version_2.domain.tournament.Sparring.SparringHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SparringHistoryRepository extends JpaRepository<SparringHistory, UUID> {
    @Query("""
                SELECT DISTINCT sh
                FROM SparringHistory sh
                    JOIN FETCH sh.sparringList sl
                    JOIN FETCH sl.student s
                    JOIN FETCH sl.sparringCombination sc
                WHERE sc = :sparringCombination
            """)
    List<SparringHistory> findAllBySparringCombination(@Param("sparringCombination") SparringCombination sparringCombination);
}
