package com.dat.backend_version_2.service.tournament.Poomsae;

import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeCombination;
import com.dat.backend_version_2.repository.tournament.Poomsae.PoomsaeCombinationRepository;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PoomsaeCombinationService {
    private final PoomsaeCombinationRepository poomsaeCombinationRepository;

    public List<PoomsaeCombination> getAllCombinations() {
        return poomsaeCombinationRepository.findAllWithGraph();
    }

    public PoomsaeCombination getCombinationById(String id) throws IdInvalidException {
        return poomsaeCombinationRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IdInvalidException("Poomsae combination not found with id: " + id));
    }

    public PoomsaeCombination createCombination(PoomsaeCombination combination) {
        return poomsaeCombinationRepository.save(combination);
    }

    public void deleteCombination(String id) {
        poomsaeCombinationRepository.deleteById(UUID.fromString(id));
    }
}
