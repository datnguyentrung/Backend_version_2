package com.dat.backend_version_2.controller.tournament.Poomsae;

import com.dat.backend_version_2.domain.achievement.PoomsaeList;
import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeHistory;
import com.dat.backend_version_2.dto.achievement.PoomsaeListDTO;
import com.dat.backend_version_2.dto.tournament.PoomsaeHistoryDTO;
import com.dat.backend_version_2.mapper.tournament.PoomsaeHistoryMapper;
import com.dat.backend_version_2.service.tournament.Poomsae.PoomsaeHistoryService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/poomsae-histories")
public class PoomsaeHistoryController {
    private final PoomsaeHistoryService poomsaeHistoryService;

    @PostMapping
    public ResponseEntity<String> createPoomsaeHistory(@RequestBody List<String> poomsaeList
    ) throws IdInvalidException {
        if (poomsaeList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Poomsae list is empty");
        }
        System.out.println(poomsaeList);
        poomsaeHistoryService.createPoomsaeHistory(poomsaeList);
        return ResponseEntity.status(HttpStatus.CREATED).body("Poomsae history created");
    }

    @GetMapping
    public ResponseEntity<List<PoomsaeHistoryDTO>> getPoomsaeHistory() {
        return ResponseEntity.ok(poomsaeHistoryService.getAllPoomsaeHistory().stream()
                .map(PoomsaeHistoryMapper::poomsaeHistoryToPoomsaeHistoryDTO)
                .toList());
    }

    @GetMapping("/combination/{idPoomsaeConbination}")
    public ResponseEntity<List<PoomsaeHistoryDTO>> getByIdPoomsaeCombination(
            @PathVariable String idPoomsaeConbination) throws IdInvalidException {
        return ResponseEntity.ok(poomsaeHistoryService.getAllPoomsaeHistoryByIdPoomsaeCombination(idPoomsaeConbination));
    }

    @PostMapping("/winner")
    public ResponseEntity<String> createPoomsaeWinner(
            @RequestParam int participants,
            @RequestBody PoomsaeHistoryDTO poomsaeHistoryDTO
    ) throws IdInvalidException {
        System.out.println("poomsaeHistoryDTO: " + poomsaeHistoryDTO);
        System.out.println("participants: " + participants);
        String newNodeId = poomsaeHistoryService.createWinner(poomsaeHistoryDTO, participants);
        return ResponseEntity.ok("Winner created successfully with new node ID: " + newNodeId);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePoomsaeHistory(
            @RequestParam int participants,
            @RequestParam String idPoomsaeHistory) throws IdInvalidException {

        // 🧩 Gọi service để xử lý logic xóa
        poomsaeHistoryService.deletePoomsaeHistory(participants, idPoomsaeHistory);

        // ✅ Trả về phản hồi thành công (204 No Content hoặc 200 OK)
        return ResponseEntity.ok("Đã xóa PoomsaeHistory thành công (id = " + idPoomsaeHistory + ")");
    }

    @DeleteMapping("/combination/{idPoomsaeCombination}")
    public ResponseEntity<String> deleteAllPoomsaeHistoryByIdPoomsaeCombination(
            @PathVariable String idPoomsaeCombination) throws IdInvalidException {
        poomsaeHistoryService.deleteAllPoomsaeHistoryByIdPoomsaeCombination(idPoomsaeCombination);
        return ResponseEntity.ok("All Poomsae histories deleted for combination id: " + idPoomsaeCombination);
    }
}
