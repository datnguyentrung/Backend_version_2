package com.dat.backend_version_2.controller.tournament.Sparring;

import com.dat.backend_version_2.dto.tournament.SparringHistoryDTO;
import com.dat.backend_version_2.mapper.tournament.SparringHistoryMapper;
import com.dat.backend_version_2.service.tournament.Sparring.SparringHistoryService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sparring-histories")
public class SparringHistoryController {
    private final SparringHistoryService sparringHistoryService;

    @PostMapping
    public ResponseEntity<String> createSparringHistory(@RequestBody List<String> sparringList
    ) throws IdInvalidException {
        if (sparringList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sparring list is empty");
        }
        System.out.println(sparringList);
        sparringHistoryService.createSparringHistory(sparringList);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sparring history created");
    }

    @GetMapping
    public ResponseEntity<List<SparringHistoryDTO>> getSparringHistory() {
        return ResponseEntity.ok(sparringHistoryService.getAllSparringHistory().stream()
                .map(SparringHistoryMapper::sparringHistoryToSparringHistoryDTO)
                .toList());
    }

    @GetMapping("/combination/{idSparringConbination}")
    public ResponseEntity<List<SparringHistoryDTO>> getByIdSparringCombination(
            @PathVariable String idSparringConbination) throws IdInvalidException {
        return ResponseEntity.ok(sparringHistoryService.getAllSparringHistoryByIdSparringCombination(idSparringConbination));
    }

    @PostMapping("/winner")
    public ResponseEntity<String> createSparringWinner(
            @RequestParam int participants,
            @RequestBody SparringHistoryDTO sparringHistoryDTO
    ) throws IdInvalidException {
        System.out.println("sparringHistoryDTO: " + sparringHistoryDTO);
        System.out.println("participants: " + participants);
        String newNodeId = sparringHistoryService.createWinner(sparringHistoryDTO, participants);
        return ResponseEntity.ok("Winner created successfully with new node ID: " + newNodeId);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSparringHistory(
            @RequestParam int participants,
            @RequestParam String idSparringHistory) throws IdInvalidException {

        // 🧩 Gọi service để xử lý logic xóa
        sparringHistoryService.deleteSparringHistory(participants, idSparringHistory);

        // ✅ Trả về phản hồi thành công (204 No Content hoặc 200 OK)
        return ResponseEntity.ok("Đã xóa SparringHistory thành công (id = " + idSparringHistory + ")");
    }

    @GetMapping("/tournament/{idTournament}")
    public ResponseEntity<List<SparringHistoryDTO>> getByIdTournament(
            @PathVariable String idTournament) throws IdInvalidException {
        return ResponseEntity.ok(sparringHistoryService.getAllSparringHistoryByIdTournament(idTournament));
    }
}
