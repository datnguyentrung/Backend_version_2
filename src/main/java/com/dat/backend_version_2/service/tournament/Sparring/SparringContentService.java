package com.dat.backend_version_2.service.tournament.Sparring;

import com.dat.backend_version_2.domain.tournament.AgeGroup;
import com.dat.backend_version_2.domain.tournament.Sparring.SparringCombination;
import com.dat.backend_version_2.domain.tournament.Sparring.SparringContent;
import com.dat.backend_version_2.enums.training.Gender;
import com.dat.backend_version_2.repository.tournament.AgeGroupRepository;
import com.dat.backend_version_2.repository.tournament.Sparring.SparringCombinationRepository;
import com.dat.backend_version_2.repository.tournament.Sparring.SparringContentRepository;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SparringContentService {
    private final SparringContentRepository sparringContentRepository;
    private final AgeGroupRepository ageGroupRepository;
    private final SparringCombinationRepository sparringCombinationRepository;

    public List<SparringContent> getAllSparringContent() {
        return sparringContentRepository.findAll();
    }

    public SparringContent getSparringContent(Integer id) throws IdInvalidException {
        return sparringContentRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Sparring Content not found with id: " + id));
    }

    public SparringContent updateSparringContent(SparringContent SparringContent) {
        return sparringContentRepository.save(SparringContent);
    }

    public void deleteSparringContent(int id) {
        sparringContentRepository.deleteById(id);
    }

    public SparringContent createSparringContent(int weightClass) throws IdInvalidException {
        SparringContent check = sparringContentRepository.findByWeightClass(weightClass);
        if (check != null) {
            throw new IdInvalidException("Hạng cân '" + weightClass + "' đã tồn tại trong hệ thống!");
        }

        SparringContent SparringContent = new SparringContent();
        SparringContent.setWeightClass(weightClass);
        sparringContentRepository.save(SparringContent);

        List<AgeGroup> ageGroups = ageGroupRepository.findAll();

        List<SparringCombination> sparringCombinations = new ArrayList<>();

        for (AgeGroup ageGroup : ageGroups) {
            // Nam
            SparringCombination maleCombination = new SparringCombination();
            maleCombination.setSparringContent(SparringContent); // chú ý: biến viết thường
            maleCombination.setAgeGroup(ageGroup);
            maleCombination.setGender(Gender.MALE);
            sparringCombinations.add(maleCombination);

            // Nữ
            SparringCombination femaleCombination = new SparringCombination();
            femaleCombination.setSparringContent(SparringContent);
            femaleCombination.setAgeGroup(ageGroup);
            femaleCombination.setGender(Gender.FEMALE);
            sparringCombinations.add(femaleCombination);
        }
        sparringCombinationRepository.saveAll(sparringCombinations);
        return SparringContent;
    }
}
