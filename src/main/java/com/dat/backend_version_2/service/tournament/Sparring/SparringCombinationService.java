package com.dat.backend_version_2.service.tournament.Sparring;

import com.dat.backend_version_2.domain.tournament.Sparring.SparringCombination;
import com.dat.backend_version_2.repository.tournament.Sparring.SparringCombinationRepository;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SparringCombinationService {
    private final SparringCombinationRepository sparringCombinationRepository;

    public List<SparringCombination> getAllCombinations() {
        return sparringCombinationRepository.findAll();
    }

    public SparringCombination getCombinationById(String id) throws IdInvalidException {
        return sparringCombinationRepository.findById(UUID.fromString(id))
                .orElseThrow(()-> new IdInvalidException("Sparring combination not found with id: " + id));
    }

    public SparringCombination createCombination(SparringCombination combination) {
        return sparringCombinationRepository.save(combination);
    }

    public void deleteCombination(String id) {
        sparringCombinationRepository.deleteById(UUID.fromString(id));
    }
}
