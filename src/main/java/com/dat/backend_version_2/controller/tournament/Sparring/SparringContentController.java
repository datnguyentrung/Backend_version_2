package com.dat.backend_version_2.controller.tournament.Sparring;

import com.dat.backend_version_2.domain.tournament.Sparring.SparringContent;
import com.dat.backend_version_2.service.tournament.Sparring.SparringContentService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sparring-contents")
public class SparringContentController {

    private final SparringContentService sparringContentService;

    // 🟢 Lấy danh sách tất cả nội dung thi đấu
    @GetMapping
    public ResponseEntity<List<SparringContent>> getAllSparringContents() {
        List<SparringContent> contents = sparringContentService.getAllSparringContent();
        return ResponseEntity.ok(contents);
    }

    // 🟢 Lấy nội dung thi đấu theo ID
    @GetMapping("/{id}")
    public ResponseEntity<SparringContent> getSparringContentById(@PathVariable Integer id) throws IdInvalidException {
        SparringContent content = sparringContentService.getSparringContent(id);
        return ResponseEntity.ok(content);
    }

    // 🟡 Tạo mới nội dung thi đấu (và tự động tạo combinations)
    @PostMapping
    public ResponseEntity<SparringContent> createSparringContent(@RequestParam int weightClass) throws IdInvalidException {
        SparringContent newContent = sparringContentService.createSparringContent(weightClass);
        return ResponseEntity.status(HttpStatus.CREATED).body(newContent);
    }

    // 🟠 Cập nhật nội dung thi đấu
    @PutMapping
    public ResponseEntity<SparringContent> updateSparringContent(@RequestBody SparringContent sparringContent) {
        SparringContent updated = sparringContentService.updateSparringContent(sparringContent);
        return ResponseEntity.ok(updated);
    }

    // 🔴 Xóa nội dung thi đấu theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSparringContent(@PathVariable Integer id) {
        sparringContentService.deleteSparringContent(id);
        return ResponseEntity.noContent().build();
    }
}
