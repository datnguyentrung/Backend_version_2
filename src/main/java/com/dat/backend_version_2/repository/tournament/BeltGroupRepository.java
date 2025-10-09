package com.dat.backend_version_2.repository.tournament;

import com.dat.backend_version_2.domain.tournament.BeltGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeltGroupRepository extends JpaRepository<BeltGroup, Integer> {
}
