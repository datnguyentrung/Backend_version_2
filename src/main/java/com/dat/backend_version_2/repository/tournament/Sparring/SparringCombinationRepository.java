package com.dat.backend_version_2.repository.tournament.Sparring;

import com.dat.backend_version_2.domain.tournament.Sparring.SparringCombination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SparringCombinationRepository extends JpaRepository<SparringCombination, UUID> {
}
