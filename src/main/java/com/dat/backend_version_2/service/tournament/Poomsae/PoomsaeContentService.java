package com.dat.backend_version_2.service.tournament.Poomsae;

import com.dat.backend_version_2.domain.tournament.AgeGroup;
import com.dat.backend_version_2.domain.tournament.BeltGroup;
import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeCombination;
import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeContent;
import com.dat.backend_version_2.enums.tournament.PoomsaeTypes;
import com.dat.backend_version_2.repository.tournament.AgeGroupRepository;
import com.dat.backend_version_2.repository.tournament.BeltGroupRepository;
import com.dat.backend_version_2.repository.tournament.Poomsae.PoomsaeCombinationRepository;
import com.dat.backend_version_2.repository.tournament.Poomsae.PoomsaeContentRepository;
import com.dat.backend_version_2.service.tournament.AgeGroupService;
import com.dat.backend_version_2.service.tournament.BeltGroupService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PoomsaeContentService {
    private final PoomsaeContentRepository poomsaeContentRepository;
    private final AgeGroupRepository ageGroupRepository;
    private final BeltGroupRepository beltGroupRepository;
    private final PoomsaeCombinationRepository poomsaeCombinationRepository;

    public List<PoomsaeContent> getAllPoomsaeContent() {
        return poomsaeContentRepository.findAll();
    }

    public PoomsaeContent getPoomsaeContent(Integer id) throws IdInvalidException {
        return poomsaeContentRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Poomsae Content not found with id: " + id));
    }

    public PoomsaeContent updatePoomsaeContent(PoomsaeContent poomsaeContent) {
        return poomsaeContentRepository.save(poomsaeContent);
    }

    public void deletePoomsaeContent(int id) {
        poomsaeContentRepository.deleteById(id);
    }

    public PoomsaeContent createPoomsaeContent(PoomsaeTypes contentName) {
        PoomsaeContent poomsaeContent = new PoomsaeContent();
        poomsaeContent.setContentName(contentName);
        poomsaeContentRepository.save(poomsaeContent);

        List<AgeGroup> ageGroups = ageGroupRepository.findAll();
        List<BeltGroup> beltGroups = beltGroupRepository.findAll();

        List<PoomsaeCombination> poomsaeCombinations = new ArrayList<>();

        for (AgeGroup ageGroup : ageGroups) {
            for (BeltGroup beltGroup : beltGroups) {
                PoomsaeCombination combination = new PoomsaeCombination();
                combination.setPoomsaeContent(poomsaeContent);
                combination.setAgeGroup(ageGroup);
                combination.setBeltGroup(beltGroup);
                poomsaeCombinations.add(combination);
            }
        }

        poomsaeCombinationRepository.saveAll(poomsaeCombinations);
        return poomsaeContent;
    }
}
