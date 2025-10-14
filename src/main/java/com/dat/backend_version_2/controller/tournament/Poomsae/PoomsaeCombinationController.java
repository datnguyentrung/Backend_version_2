package com.dat.backend_version_2.controller.tournament.Poomsae;

import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeCombination;
import com.dat.backend_version_2.service.tournament.Poomsae.PoomsaeCombinationService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/poomsae-combinations")
public class PoomsaeCombinationController {
    private final PoomsaeCombinationService poomsaeCombinationService;

    @GetMapping
    public ResponseEntity<List<PoomsaeCombination>> getAllCombinations() {
        List<PoomsaeCombination> combinations = poomsaeCombinationService.getAllCombinations();
        return ResponseEntity.ok(combinations);
    }

    @PatchMapping("/change-active")
    public ResponseEntity<String> activateCombinations(
            @RequestParam (required = false, defaultValue = "true") boolean activate,
            @RequestParam String idPoomsaeCombination) throws IdInvalidException {
        poomsaeCombinationService.changeActiveStatus(idPoomsaeCombination, activate);
        return ResponseEntity.ok("All combinations changed active status to " + activate);
    }
}
