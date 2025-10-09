package com.dat.backend_version_2.controller.tournament.Sparring;

import com.dat.backend_version_2.domain.tournament.Sparring.SparringCombination;
import com.dat.backend_version_2.service.tournament.Sparring.SparringCombinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sparring-combinations")
public class SparringCombinationController {
    private final SparringCombinationService sparringCombinationService;

    @GetMapping
    public ResponseEntity<List<SparringCombination>> getAllCombinations() {
        List<SparringCombination> combinations = sparringCombinationService.getAllCombinations();
        return ResponseEntity.ok(combinations);
    }
}
