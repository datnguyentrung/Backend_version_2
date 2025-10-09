package com.dat.backend_version_2.repository.tournament;

import com.dat.backend_version_2.domain.tournament.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, UUID> {
    Tournament findByTournamentNameAndTournamentDate(String tournamentName, LocalDate tournamentDate);
}
