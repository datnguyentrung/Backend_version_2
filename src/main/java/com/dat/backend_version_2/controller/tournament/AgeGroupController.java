package com.dat.backend_version_2.controller.tournament;

import com.dat.backend_version_2.service.tournament.AgeGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dat.backend_version_2.domain.tournament.AgeGroup;
import com.dat.backend_version_2.dto.tournament.AgeGroupDTO;
import com.dat.backend_version_2.util.error.IdInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/age-groups")
public class AgeGroupController {

    private final AgeGroupService ageGroupService;

    // 🟢 Lấy tất cả lứa tuổi
    @GetMapping
    public ResponseEntity<List<AgeGroup>> getAllAgeGroups() {
        List<AgeGroup> ageGroups = ageGroupService.getAllAgeGroups();
        return ResponseEntity.ok(ageGroups);
    }

    // 🟢 Lấy 1 lứa tuổi theo ID
    @GetMapping("/{id}")
    public ResponseEntity<AgeGroup> getAgeGroupById(@PathVariable int id) throws IdInvalidException {
        AgeGroup ageGroup = ageGroupService.getAgeGroupById(id);
        return ResponseEntity.ok(ageGroup);
    }

    // 🟡 Tạo mới lứa tuổi + tự động sinh combinations
    @PostMapping
    public ResponseEntity<AgeGroup> createAgeGroup(@RequestBody AgeGroupDTO ageGroupDTO) {
        AgeGroup newAgeGroup = ageGroupService.createAgeGroup(ageGroupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAgeGroup);
    }

    // 🟠 Cập nhật thông tin lứa tuổi
    @PutMapping
    public ResponseEntity<AgeGroup> updateAgeGroup(@RequestBody AgeGroup ageGroup) {
        AgeGroup updated = ageGroupService.update(ageGroup);
        return ResponseEntity.ok(updated);
    }

    // 🔴 Xóa lứa tuổi
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgeGroup(@PathVariable int id) {
        ageGroupService.deleteAgeGroup(id);
        return ResponseEntity.noContent().build();
    }
}
