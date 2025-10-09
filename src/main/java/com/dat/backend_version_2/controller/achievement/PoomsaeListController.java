package com.dat.backend_version_2.controller.achievement;

import com.dat.backend_version_2.domain.achievement.PoomsaeList;
import com.dat.backend_version_2.dto.achievement.PoomsaeListDTO;
import com.dat.backend_version_2.mapper.achievement.PoomsaeListMapper;
import com.dat.backend_version_2.service.achievement.PoomsaeListService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/poomsae-lists")
public class PoomsaeListController {
    private final PoomsaeListService poomsaeListService;

    @GetMapping
    public ResponseEntity<List<PoomsaeListDTO>> getAll() {
        List<PoomsaeListDTO> result = poomsaeListService.getAllPoomsaeList()
                .stream()
                .map(PoomsaeListMapper::poomsaeListToDTO)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoomsaeList> getById(@PathVariable String id
    ) throws IdInvalidException {
        return ResponseEntity.ok(poomsaeListService.getPoomsaeListById(id));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody List<PoomsaeListDTO.CompetitorDTO> competitorDTOS) {
        try {
            poomsaeListService.createPoomsaeList(competitorDTOS);
            return ResponseEntity.ok("All Poomsae lists created successfully");
        } catch (IdInvalidException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error while creating Poomsae lists");
        }
    }

    @GetMapping("/tournament/{idTournament}")
    public ResponseEntity<List<PoomsaeListDTO>> getByIdTournament(
            @PathVariable String idTournament) throws IdInvalidException {
        List<PoomsaeListDTO> poomsaeListDTOS = poomsaeListService
                .getPoomsaeListByIdTournament(idTournament)
                .stream()
                .map(PoomsaeListMapper::poomsaeListToDTO)
                .toList();
        return ResponseEntity.ok(poomsaeListDTOS);
    }
}
