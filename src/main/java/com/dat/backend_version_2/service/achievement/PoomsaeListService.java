package com.dat.backend_version_2.service.achievement;

import com.dat.backend_version_2.domain.achievement.PoomsaeList;
import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeCombination;
import com.dat.backend_version_2.domain.tournament.Tournament;
import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.dto.achievement.PoomsaeListDTO;
import com.dat.backend_version_2.repository.achievement.PoomsaeListRepository;
import com.dat.backend_version_2.service.tournament.Poomsae.PoomsaeCombinationService;
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
public class PoomsaeListService {
    private final PoomsaeListRepository poomsaeListRepository;
    private final StudentService studentService;
    private final TournamentService tournamentService;
    private final PoomsaeCombinationService poomsaeCombinationService;

    public List<PoomsaeList> getAllPoomsaeList() {
        return poomsaeListRepository.findAll();
    }

    public PoomsaeList getPoomsaeListById(String idPoomsaeList) throws IdInvalidException {
        return poomsaeListRepository.findById(UUID.fromString(idPoomsaeList))
                .orElseThrow(() -> new IdInvalidException("PoomsaeList not found with id: " + idPoomsaeList));
    }

    public List<PoomsaeList> getPoomsaeListByIdTournament(String idTournament) throws IdInvalidException {
        Tournament tournament = tournamentService.getTournamentById(idTournament);
        return poomsaeListRepository.findByTournament(tournament);
    }

    @Transactional
    public void createPoomsaeList(List<PoomsaeListDTO.CompetitorDTO> competitors) throws IdInvalidException {
        if (competitors == null || competitors.isEmpty()) {
            throw new IdInvalidException("Competitor list is empty");
        }

        List<PoomsaeList> poomsaeLists = new ArrayList<>(competitors.size());

        String tournamentId = "a8d5c830-c275-41b0-a251-294eb61c007f";
        for (PoomsaeListDTO.CompetitorDTO competitor : competitors) {
            // Lấy sẵn thông tin
            Student student = studentService.getStudentByIdAccount(competitor.getPersonalAcademicInfo().getPersonalInfo().getIdAccount());
            if (student == null)
                throw new IdInvalidException("Student not found: " + competitor.getPersonalAcademicInfo().getPersonalInfo().getIdAccount());
            if (!student.getIsActive())
                throw new IdInvalidException("Student is not active: " + competitor.getPersonalAcademicInfo().getPersonalInfo().getIdAccount());

//            String tournamentId = competitor.getCompetition().getIdTournament();
            String poomsaeCombId = competitor.getCompetition().getIdPoomsaeCombination();

            Tournament tournament = tournamentService.getTournamentById(tournamentId);
            PoomsaeCombination combination = poomsaeCombinationService.getCombinationById(poomsaeCombId);

            if (tournament == null)
                throw new IdInvalidException("Tournament not found: " + tournamentId);
            if (combination == null)
                throw new IdInvalidException("Poomsae Combination not found: " + poomsaeCombId);

            // Build entity
            PoomsaeList entity = new PoomsaeList();
            entity.setStudent(student);
            entity.setTournament(tournament);
            entity.setPoomsaeCombination(combination);
            entity.setMedal(competitor.getMedal());
            poomsaeLists.add(entity);
        }

        // Batch save
        poomsaeListRepository.saveAll(poomsaeLists);
    }
}
