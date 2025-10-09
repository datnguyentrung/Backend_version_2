package com.dat.backend_version_2.controller.tournament;

import com.dat.backend_version_2.domain.tournament.Tournament;
import com.dat.backend_version_2.dto.tournament.TournamentDTO;
import com.dat.backend_version_2.service.tournament.TournamentService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tournaments")
public class TournamentController {
    private final TournamentService tournamentService;

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @PostMapping
    public ResponseEntity<String> createTournament(
            @RequestBody TournamentDTO.TournamentInfo tournamentInfo) {
        try {
            tournamentService.createNewTournament(tournamentInfo);
            return ResponseEntity.ok("Tournament created successfully");
        } catch (IdInvalidException e) {
            return ResponseEntity.badRequest().body("Invalid tournament ID");
        }
    }
}
