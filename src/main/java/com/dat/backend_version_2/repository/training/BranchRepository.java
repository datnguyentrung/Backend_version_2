package com.dat.backend_version_2.repository.training;

import com.dat.backend_version_2.domain.training.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    @Query("SELECT DISTINCT b FROM Branch b LEFT JOIN FETCH b.weekdays")
    List<Branch> findAllWithWeekdays();
}
