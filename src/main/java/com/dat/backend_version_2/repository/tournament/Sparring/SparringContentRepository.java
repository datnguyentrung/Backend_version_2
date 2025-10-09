package com.dat.backend_version_2.repository.tournament.Sparring;

import com.dat.backend_version_2.domain.tournament.Sparring.SparringContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SparringContentRepository extends JpaRepository<SparringContent, Integer> {
    SparringContent findByWeightClass(int weightClass);
}
