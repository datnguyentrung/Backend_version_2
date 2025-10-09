package com.dat.backend_version_2.repository.achievement;

import com.dat.backend_version_2.domain.achievement.SparringList;
import com.dat.backend_version_2.domain.tournament.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SparringListRepository extends JpaRepository<SparringList, UUID> {
    @Query("""
            SELECT DISTINCT s
            FROM SparringList s
            JOIN FETCH s.student st
            JOIN FETCH s.tournament t
            JOIN FETCH s.branch b
            JOIN FETCH s.sparringCombination sc
            WHERE s.idSparringList IN :ids
            """)
    List<SparringList> findAllByIdSparringList(@Param("ids") List<UUID> ids);

    List<SparringList> findByTournament(Tournament tournament);
}
