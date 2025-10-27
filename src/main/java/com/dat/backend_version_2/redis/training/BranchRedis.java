package com.dat.backend_version_2.redis.training;

import com.dat.backend_version_2.domain.training.Branch;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface BranchRedis {
    void clear();

    List<Branch> getAllBranches() throws JsonProcessingException;

    Branch getBranchById(int id) throws JsonProcessingException;

    void saveBranchById(int idBranch, Branch branch) throws JsonProcessingException;

    void saveAllBranches(List<Branch> branches) throws JsonProcessingException;
}
