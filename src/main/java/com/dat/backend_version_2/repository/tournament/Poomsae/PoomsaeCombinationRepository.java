package com.dat.backend_version_2.repository.tournament.Poomsae;

import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeCombination;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PoomsaeCombinationRepository extends JpaRepository<PoomsaeCombination, UUID> {
    @EntityGraph(attributePaths = {
            "poomsaeContent",
            "ageGroup",
            "beltGroup"
    })
    @Query("SELECT DISTINCT pc FROM PoomsaeCombination pc")
    List<PoomsaeCombination> findAllWithGraph();
}
