package com.dat.backend_version_2.service.tournament;

import com.dat.backend_version_2.domain.tournament.BracketNode;
import com.dat.backend_version_2.repository.tournament.BracketNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BracketNodeTransactionalService {

    private final BracketNodeRepository bracketNodeRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createBracketNodeAllowParticipants(List<BracketNode> bracketNodes) {
        if (bracketNodes == null || bracketNodes.isEmpty()) return;

        bracketNodeRepository.saveAll(bracketNodes);
        bracketNodeRepository.flush(); // đảm bảo commit ngay để có thể đọc lại liền
    }
}
