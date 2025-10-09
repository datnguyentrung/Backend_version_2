package com.dat.backend_version_2.mapper.training;

import com.dat.backend_version_2.domain.training.Branch;
import com.dat.backend_version_2.dto.training.Branch.BranchReq;

public class BranchMapper {
    public static Branch branchInfoToBranch(BranchReq.BranchInfo branchInfo) {
        if (branchInfo == null) {
            return null;
        }
        Branch branch = new Branch();

        // Basic Info
        branch.setTitle(branchInfo.getBasic().getTitle());
        branch.setAvatar(branchInfo.getBasic().getAvatar());
        branch.setIsNew(branchInfo.getBasic().getIsNew());

        // Location Info
        branch.setAddress(branchInfo.getLocation().getAddress());
        branch.setWeekdays(branchInfo.getLocation().getWeekdays());

        return branch;
    }
}
