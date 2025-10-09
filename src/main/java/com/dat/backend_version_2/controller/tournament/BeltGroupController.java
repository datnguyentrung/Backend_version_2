package com.dat.backend_version_2.controller.tournament;

import com.dat.backend_version_2.domain.tournament.BeltGroup;
import com.dat.backend_version_2.dto.tournament.BeltGroupDTO;
import com.dat.backend_version_2.service.tournament.BeltGroupService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/belt-groups")
public class BeltGroupController {

    private final BeltGroupService beltGroupService;

    // 🟢 Lấy tất cả nhóm đai
    @GetMapping
    public ResponseEntity<List<BeltGroup>> getAllBeltGroups() {
        List<BeltGroup> beltGroups = beltGroupService.getAllBeltGroups();
        return ResponseEntity.ok(beltGroups);
    }

    // 🟢 Lấy 1 nhóm đai theo ID
    @GetMapping("/{id}")
    public ResponseEntity<BeltGroup> getBeltGroupById(@PathVariable int id) throws IdInvalidException {
        BeltGroup beltGroup = beltGroupService.getBeltGroupById(id);
        return ResponseEntity.ok(beltGroup);
    }

    // 🟡 Tạo mới nhóm đai + tự động sinh combinations
    @PostMapping
    public ResponseEntity<BeltGroup> createBeltGroup(@RequestBody BeltGroupDTO beltGroupDTO) {
        BeltGroup newBeltGroup = beltGroupService.createBeltGroup(beltGroupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBeltGroup);
    }

    // 🟠 Cập nhật nhóm đai
    @PutMapping
    public ResponseEntity<BeltGroup> updateBeltGroup(@RequestBody BeltGroup beltGroup) {
        BeltGroup updated = beltGroupService.updateBeltGroup(beltGroup);
        return ResponseEntity.ok(updated);
    }

    // 🔴 Xóa nhóm đai
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeltGroup(@PathVariable int id) {
        beltGroupService.deleteBeltGroup(id);
        return ResponseEntity.noContent().build();
    }
}