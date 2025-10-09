package com.dat.backend_version_2.service.achievement;

import com.dat.backend_version_2.domain.achievement.SparringList;
import com.dat.backend_version_2.domain.tournament.Sparring.SparringCombination;
import com.dat.backend_version_2.domain.tournament.Tournament;
import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.dto.achievement.SparringListDTO;
import com.dat.backend_version_2.repository.achievement.SparringListRepository;
import com.dat.backend_version_2.service.tournament.Sparring.SparringCombinationService;
import com.dat.backend_version_2.service.tournament.TournamentService;
import com.dat.backend_version_2.service.training.StudentService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SparringListService {
    private final SparringListRepository sparringListRepository;
    private final StudentService studentService;
    private final TournamentService tournamentService;
    private final SparringCombinationService sparringCombinationService;

    public List<SparringList> getAllSparringList() {
        return sparringListRepository.findAll();
    }

    public SparringList getSparringListById(String idSparringList) throws IdInvalidException {
        return sparringListRepository.findById(UUID.fromString(idSparringList))
                .orElseThrow(() -> new IdInvalidException("SparringList not found with id: " + idSparringList));
    }

    public List<SparringList> getSparringListByIdTournament(String idTournament) throws IdInvalidException {
        Tournament tournament = tournamentService.getTournamentById(idTournament);
        return sparringListRepository.findByTournament(tournament);
    }

    @Transactional
    public void createSparringList(List<SparringListDTO.CompetitorDTO> competitors) throws IdInvalidException {
        if (competitors == null || competitors.isEmpty()) {
            throw new IdInvalidException("Competitor list is empty");
        }

        List<SparringList> sparringLists = new ArrayList<>(competitors.size());

        String tournamentId = "a8d5c830-c275-41b0-a251-294eb61c007f";
        for (SparringListDTO.CompetitorDTO competitor : competitors) {
            // Lấy sẵn thông tin
            Student student = studentService.getStudentByIdAccount(competitor.getPersonalAcademicInfo().getPersonalInfo().getIdAccount());
            if (student == null)
                throw new IdInvalidException("Student not found: " + competitor.getPersonalAcademicInfo().getPersonalInfo().getIdAccount());
            if (!student.getIsActive())
                throw new IdInvalidException("Student is not active: " + competitor.getPersonalAcademicInfo().getPersonalInfo().getIdAccount());

//            String tournamentId = competitor.getCompetition().getIdTournament();
            String sparringCombId = competitor.getCompetition().getIdSparringCombination();

            Tournament tournament = tournamentService.getTournamentById(tournamentId);
            SparringCombination combination = sparringCombinationService.getCombinationById(sparringCombId);

            if (tournament == null)
                throw new IdInvalidException("Tournament not found: " + tournamentId);
            if (combination == null)
                throw new IdInvalidException("Sparring Combination not found: " + sparringCombId);

            // Build entity
            SparringList entity = new SparringList();
            entity.setStudent(student);
            entity.setTournament(tournament);
            entity.setSparringCombination(combination);
            entity.setMedal(competitor.getMedal());
            sparringLists.add(entity);
        }

        // Batch save
        sparringListRepository.saveAll(sparringLists);
    }
}
