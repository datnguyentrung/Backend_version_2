package com.dat.backend_version_2.repository.tournament.Sparring;

import com.dat.backend_version_2.domain.tournament.Tournament;
import com.dat.backend_version_2.domain.tournament.TournamentMatch;
import com.dat.backend_version_2.dto.tournament.TournamentMatchId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentMatchRepository extends JpaRepository<TournamentMatch, TournamentMatchId> {
    List<TournamentMatch> findAllByTournament(Tournament tournament);
}
