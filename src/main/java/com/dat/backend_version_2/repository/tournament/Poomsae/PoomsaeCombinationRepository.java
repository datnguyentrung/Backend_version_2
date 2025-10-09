package com.dat.backend_version_2.repository.tournament.Poomsae;

import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeCombination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PoomsaeCombinationRepository extends JpaRepository<PoomsaeCombination, UUID> {
}
