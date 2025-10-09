package com.dat.backend_version_2.service.tournament;

import com.dat.backend_version_2.domain.tournament.AgeGroup;
import com.dat.backend_version_2.domain.tournament.BeltGroup;
import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeCombination;
import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeContent;
import com.dat.backend_version_2.dto.tournament.BeltGroupDTO;
import com.dat.backend_version_2.repository.tournament.AgeGroupRepository;
import com.dat.backend_version_2.repository.tournament.BeltGroupRepository;
import com.dat.backend_version_2.repository.tournament.Poomsae.PoomsaeCombinationRepository;
import com.dat.backend_version_2.service.tournament.Poomsae.PoomsaeContentService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeltGroupService {
    private final BeltGroupRepository beltGroupRepository;
    private final AgeGroupRepository ageGroupRepository;
    private final PoomsaeContentService poomsaeContentService;
    private final PoomsaeCombinationRepository poomsaeCombinationRepository;

    public List<BeltGroup> getAllBeltGroups() {
        return beltGroupRepository.findAll();
    }

    public BeltGroup getBeltGroupById(int id) throws IdInvalidException {
        return beltGroupRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Belt group not found with id: " + id));
    }

    public BeltGroup updateBeltGroup(BeltGroup beltGroup) {
        return beltGroupRepository.save(beltGroup);
    }

    public void deleteBeltGroup(int id) {
        beltGroupRepository.deleteById(id);
    }

    public BeltGroup createBeltGroup(BeltGroupDTO beltGroupDTO) {
        // 1️⃣ Tạo mới BeltGroup
        BeltGroup beltGroup = new BeltGroup();
        beltGroup.setBeltGroupName(beltGroupDTO.getBeltGroupName());
        beltGroup.setBeltLevels(beltGroupDTO.getBeltLevels());
        beltGroup.setIsActive(true);
        beltGroupRepository.save(beltGroup);

        // 2️⃣ Lấy toàn bộ AgeGroup và PoomsaeContent hiện có
        List<AgeGroup> ageGroups = ageGroupRepository.findAll();
        List<PoomsaeContent> poomsaeContents = poomsaeContentService.getAllPoomsaeContent();

        List<PoomsaeCombination> poomsaeCombinations = new ArrayList<>();

        // 3️⃣ Tạo tổ hợp cho từng nội dung thi đấu và lứa tuổi
        for (PoomsaeContent poomsaeContent : poomsaeContents) {
            for (AgeGroup ageGroup : ageGroups) {
                PoomsaeCombination combination = new PoomsaeCombination();
                combination.setPoomsaeContent(poomsaeContent);
                combination.setAgeGroup(ageGroup);
                combination.setBeltGroup(beltGroup);
                poomsaeCombinations.add(combination);
            }
        }

        // 4️⃣ Lưu toàn bộ combination vào DB
        poomsaeCombinationRepository.saveAll(poomsaeCombinations);

        return beltGroup;
    }
}
