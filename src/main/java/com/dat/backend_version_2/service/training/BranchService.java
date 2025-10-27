package com.dat.backend_version_2.service.training;

import com.dat.backend_version_2.domain.training.Branch;
import com.dat.backend_version_2.dto.training.Branch.BranchReq;
import com.dat.backend_version_2.mapper.training.BranchMapper;
import com.dat.backend_version_2.redis.training.BranchRedisImpl;
import com.dat.backend_version_2.repository.training.BranchRepository;
import com.dat.backend_version_2.util.error.IdInvalidException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    private final BranchRedisImpl branchRedis;

    public Branch createBranch(BranchReq.BranchInfo branchInfo) {
        Branch branch = BranchMapper.branchInfoToBranch(branchInfo);
        return branchRepository.save(branch);
    }

    public List<Branch> getAllBranches(){
        return branchRepository.findAllWithWeekdays();
    }

    public Branch getBranchById(int id) throws IdInvalidException, JsonProcessingException {
        Branch branch = branchRedis.getBranchById(id);

        if (branch == null) {
            branch = branchRepository.findById(id)
                    .orElseThrow(() -> new IdInvalidException("Branch not found with id: " + id));
            branchRedis.saveBranchById(id, branch);
        }
        return branch;
    }

}
