package com.dat.backend_version_2.repository.tournament;

import com.dat.backend_version_2.domain.tournament.AgeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeGroupRepository extends JpaRepository<AgeGroup, Integer> {
}
