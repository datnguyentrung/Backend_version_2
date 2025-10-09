package com.dat.backend_version_2.controller.training;

import com.dat.backend_version_2.domain.training.Branch;
import com.dat.backend_version_2.dto.training.Branch.BranchReq;
import com.dat.backend_version_2.service.training.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody BranchReq.BranchInfo branchReq) {
        Branch branch = branchService.createBranch(branchReq);
        URI location = URI.create("/api/v1/branches/" + branch.getIdBranch()); // hoặc idStudent

        return ResponseEntity
                .created(location) // HTTP 201 + header "Location"
                .body(branch);
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        List<Branch> branches = branchService.getAllBranches();
        return ResponseEntity.ok(branches);
    }
}
