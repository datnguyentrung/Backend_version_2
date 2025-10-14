package com.dat.backend_version_2.service.tournament.Poomsae;

import com.dat.backend_version_2.domain.achievement.PoomsaeList;
import com.dat.backend_version_2.domain.tournament.BracketNode;
import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeCombination;
import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeHistory;
import com.dat.backend_version_2.dto.tournament.BracketNodeReq;
import com.dat.backend_version_2.dto.tournament.PoomsaeHistoryDTO;
import com.dat.backend_version_2.mapper.tournament.PoomsaeHistoryMapper;
import com.dat.backend_version_2.repository.achievement.PoomsaeListRepository;
import com.dat.backend_version_2.repository.tournament.Poomsae.PoomsaeHistoryRepository;
import com.dat.backend_version_2.service.tournament.BracketNodeService;
import com.dat.backend_version_2.util.error.IdInvalidException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PoomsaeHistoryService {
    private final PoomsaeHistoryRepository poomsaeHistoryRepository;
    private final BracketNodeService bracketNodeService;
    private final PoomsaeListRepository poomsaeListRepository;
    private final PoomsaeCombinationService poomsaeCombinationService;
    private static final Logger log = LoggerFactory.getLogger(PoomsaeHistoryService.class);

    public PoomsaeHistory getPoomsaeHistoryById(String id) throws IdInvalidException {
        return poomsaeHistoryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IdInvalidException("Poomsae history not found with id: " + id));
    }

    @Transactional
    public void createPoomsaeHistory(List<String> idPoomsaeList) throws IdInvalidException {
        // 1️⃣ Lấy toàn bộ danh sách trong 1 lần query
        List<UUID> uuidList = idPoomsaeList.stream()
                .map(UUID::fromString)
                .toList();

        List<PoomsaeList> poomsaeLists = poomsaeListRepository.findAllByIdPoomsaeList(uuidList);

        if (poomsaeLists.isEmpty()) {
            throw new IdInvalidException("No valid PoomsaeList found for provided IDs");
        }

        int participants = poomsaeLists.size();

        // 2️⃣ Lấy danh sách sơ đồ thi đấu
        List<BracketNodeReq.BracketInfo> bracketInfos = bracketNodeService
                .getBracketNodeByParticipants(participants);

        // 3️⃣ Chuẩn bị iterator để gán tuần tự (không quan tâm thứ tự)
        Iterator<PoomsaeList> iterator = poomsaeLists.iterator();

        // 4️⃣ Duyệt qua từng node và tạo PoomsaeHistory
        for (BracketNodeReq.BracketInfo bracketInfo : bracketInfos) {
            if (bracketInfo.getBracketNodes().size() == 1 && iterator.hasNext()) {
                PoomsaeHistory poomsaeHistory = new PoomsaeHistory();
                poomsaeHistory.setPoomsaeList(iterator.next());
//                poomsaeHistory.setPoomsaeCombination();
                poomsaeHistory.setSourceNode(bracketInfo.getChildNodeId());
                poomsaeHistory.setTargetNode(
                        bracketNodeService.getBracketNodeByParticipantsAndChildNodeId(
                                participants,
                                bracketInfo.getChildNodeId()
                        ).getParentNodeId()
                );
                poomsaeHistory.setLevelNode(bracketInfo.getLevelNode());

                poomsaeHistoryRepository.save(poomsaeHistory);
            }
        }
    }

    public List<PoomsaeHistory> getAllPoomsaeHistory() {
        return poomsaeHistoryRepository.findAll();
    }

    public List<PoomsaeHistoryDTO> getAllPoomsaeHistoryByIdPoomsaeCombination(
            String idPoomsaeCombination) throws IdInvalidException {
        PoomsaeCombination combination = poomsaeCombinationService.getCombinationById(idPoomsaeCombination);
        return poomsaeHistoryRepository.findAllByPoomsaeCombination(combination).stream()
                .map(PoomsaeHistoryMapper::poomsaeHistoryToPoomsaeHistoryDTO)
                .toList();
    }

    @Transactional
    public void deleteAllPoomsaeHistoryByIdPoomsaeCombination(String idPoomsaeCombination) throws IdInvalidException {
        PoomsaeCombination combination = poomsaeCombinationService.getCombinationById(idPoomsaeCombination);
        List<PoomsaeHistory> histories = poomsaeHistoryRepository.findAllByPoomsaeCombination(combination);
        poomsaeHistoryRepository.deleteAll(histories);
    }

    @Transactional
    public String createWinner(PoomsaeHistoryDTO poomsaeHistoryDTO, int participants) throws IdInvalidException {
        // Lấy lịch sử thi đấu hiện tại
        PoomsaeHistory poomsaeHistory = getPoomsaeHistoryById(poomsaeHistoryDTO.getIdPoomsaeHistory());
        if (poomsaeHistory == null) {
            throw new IdInvalidException("PoomsaeHistory not found");
        }

        // Đánh dấu đã thắng
        poomsaeHistory.setHasWon(true);
        poomsaeHistoryRepository.save(poomsaeHistory);

        // Lấy target node cha
        Integer parentNode = bracketNodeService.getBracketNodeByParticipantsAndChildNodeId(
                participants,
                poomsaeHistory.getTargetNode()
        ).getParentNodeId();
        if (poomsaeHistoryDTO.getNodeInfo().getTargetNode() != 0 && parentNode == null) {
            throw new IdInvalidException("Parent node not found for targetNode " + poomsaeHistory.getTargetNode());
        }

        // Tạo node mới cho vòng tiếp theo
        PoomsaeHistory newPoomsaeHistory = new PoomsaeHistory();
        newPoomsaeHistory.setLevelNode(poomsaeHistory.getLevelNode() - 1);
        newPoomsaeHistory.setSourceNode(poomsaeHistory.getTargetNode());
        newPoomsaeHistory.setTargetNode(parentNode);

        // Sao chép thông tin liên kết
        newPoomsaeHistory.setPoomsaeList(poomsaeHistory.getPoomsaeList());

        poomsaeHistoryRepository.save(newPoomsaeHistory);

        return newPoomsaeHistory.getIdPoomsaeHistory().toString();
    }

    /**
     * Xóa một bản ghi PoomsaeHistory và cập nhật trạng thái các node con liên quan.
     *
     * Quy trình:
     * 1. Lấy thông tin PoomsaeHistory cần xóa.
     * 2. Tìm tất cả node con (child nodes) trong cây bracket tương ứng.
     * 3. Lấy toàn bộ PoomsaeHistory trong cùng tổ hợp (combination).
     * 4. Tạo Map để truy cập nhanh theo childNodeId.
     * 5. Cập nhật trạng thái hasWon=false cho các node con.
     * 6. Cuối cùng, xóa bản ghi hiện tại.
     *
     * @param participants        Số lượng người tham gia (để xác định bracket).
     * @param idPoomsaeHistory    ID của bản ghi cần xóa.
     * @throws IdInvalidException Nếu không tìm thấy bản ghi hoặc dữ liệu không hợp lệ.
     */
    public void deletePoomsaeHistory(int participants, String idPoomsaeHistory) throws IdInvalidException {

        // 1️⃣ Lấy ra bản ghi PoomsaeHistory cần xóa
        PoomsaeHistory poomsaeHistory = getPoomsaeHistoryById(idPoomsaeHistory);

        // 2️⃣ Lấy danh sách các child node ID (các node con của source node hiện tại)
        List<Integer> childNodeIds = bracketNodeService
                .getBracketNodeByParticipantsAndParentNodeId(participants, poomsaeHistory.getSourceNode())
                .stream()
                .map(BracketNode::getChildNodeId)
                .toList();

        // 3️⃣ Lấy toàn bộ danh sách PoomsaeHistory thuộc cùng tổ hợp (combination)
        List<PoomsaeHistory> combination = poomsaeHistoryRepository.findAllByPoomsaeCombination(
                poomsaeHistory.getPoomsaeList().getPoomsaeCombination());

        // 4️⃣ Tạo map để tra cứu nhanh PoomsaeHistory theo targetNode (childNodeId)
        Map<Integer, PoomsaeHistory> poomsaeHistoryMap = combination.stream()
                .collect(Collectors.toMap(
                        PoomsaeHistory::getSourceNode,       // key: childNodeId (sourceNode)
                        Function.identity(),                  // value: đối tượng PoomsaeHistory
                        (existing, replacement) -> existing    // giữ bản ghi đầu tiên nếu trùng key
                ));

        // 5️⃣ Duyệt qua từng child node, cập nhật trạng thái hasWon=false
        for (Integer childNodeId : childNodeIds) {
            PoomsaeHistory childHistory = poomsaeHistoryMap.get(childNodeId);

            if (childHistory == null) {
                // Nếu không tìm thấy, ném lỗi rõ ràng
//                throw new IdInvalidException("Không tìm thấy PoomsaeHistory cho childNodeId: " + childNodeId);
                log.warn("Không tìm thấy PoomsaeHistory cho childNodeId: {}", childNodeId);
                continue;
            }

            // Cập nhật trạng thái thắng thành false
            childHistory.setHasWon(false);
            poomsaeHistoryRepository.save(childHistory);
        }

        // 6️⃣ Xóa node cha nếu node hiện tại chiến thắng
        PoomsaeHistory parentPoomsaeHistory = poomsaeHistoryMap.get(poomsaeHistory.getTargetNode());
        if (parentPoomsaeHistory != null) {
            poomsaeHistoryRepository.delete(parentPoomsaeHistory);
        }

        // 7️⃣ Xóa bản ghi gốc
        poomsaeHistoryRepository.delete(poomsaeHistory);
    }

    public Integer countPoomsaeHistoryByLevelNode(int levelNode) {
        return poomsaeHistoryRepository.countPoomsaeHistoryByLevelNode(levelNode);
    }
}
