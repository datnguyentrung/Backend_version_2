package com.dat.backend_version_2.service.tournament.Sparring;

import com.dat.backend_version_2.domain.achievement.SparringList;
import com.dat.backend_version_2.domain.tournament.BracketNode;
import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeHistory;
import com.dat.backend_version_2.domain.tournament.Sparring.SparringCombination;
import com.dat.backend_version_2.domain.tournament.Sparring.SparringHistory;
import com.dat.backend_version_2.dto.tournament.BracketNodeReq;
import com.dat.backend_version_2.dto.tournament.SparringHistoryDTO;
import com.dat.backend_version_2.mapper.tournament.SparringHistoryMapper;
import com.dat.backend_version_2.repository.achievement.SparringListRepository;
import com.dat.backend_version_2.repository.tournament.Sparring.SparringHistoryRepository;
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
public class SparringHistoryService {
    private final SparringHistoryRepository sparringHistoryRepository;
    private final BracketNodeService bracketNodeService;
    private final SparringListRepository sparringListRepository;
    private final SparringCombinationService sparringCombinationService;
    private static final Logger log = LoggerFactory.getLogger(SparringHistoryService.class);

    public SparringHistory getSparringHistoryById(String id) throws IdInvalidException {
        return sparringHistoryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IdInvalidException("Sparring history not found with id: " + id));
    }

    @Transactional
    public void createSparringHistory(List<String> idSparringList) throws IdInvalidException {
        // 1️⃣ Lấy toàn bộ danh sách trong 1 lần query
        List<UUID> uuidList = idSparringList.stream()
                .map(UUID::fromString)
                .toList();

        List<SparringList> sparringLists = sparringListRepository.findAllByIdSparringList(uuidList);

        if (sparringLists.isEmpty()) {
            throw new IdInvalidException("No valid SparringList found for provided IDs");
        }

        int participants = sparringLists.size();

        // 2️⃣ Lấy danh sách sơ đồ thi đấu
        List<BracketNodeReq.BracketInfo> bracketInfos = bracketNodeService
                .getBracketNodeByParticipants(participants);

        // 3️⃣ Chuẩn bị iterator để gán tuần tự (không quan tâm thứ tự)
        Iterator<SparringList> iterator = sparringLists.iterator();

        // 4️⃣ Duyệt qua từng node và tạo SparringHistory
        for (BracketNodeReq.BracketInfo bracketInfo : bracketInfos) {
            if (bracketInfo.getBracketNodes().size() == 1 && iterator.hasNext()) {
                SparringHistory sparringHistory = new SparringHistory();
                sparringHistory.setSparringList(iterator.next());
//                sparringHistory.setSparringCombination();
                sparringHistory.setSourceNode(bracketInfo.getChildNodeId());
                sparringHistory.setTargetNode(
                        bracketNodeService.getBracketNodeByParticipantsAndChildNodeId(
                                participants,
                                bracketInfo.getChildNodeId()
                        ).getParentNodeId()
                );
                sparringHistory.setLevelNode(bracketInfo.getLevelNode());

                sparringHistoryRepository.save(sparringHistory);
            }
        }
    }

    public List<SparringHistory> getAllSparringHistory() {
        return sparringHistoryRepository.findAll();
    }

    public List<SparringHistoryDTO> getAllSparringHistoryByIdSparringCombination(
            String idSparringCombination) throws IdInvalidException {
        SparringCombination combination = sparringCombinationService.getCombinationById(idSparringCombination);
        return sparringHistoryRepository.findAllBySparringCombination(combination).stream()
                .map(SparringHistoryMapper::sparringHistoryToSparringHistoryDTO)
                .toList();
    }

    @Transactional
    public String createWinner(SparringHistoryDTO sparringHistoryDTO, int participants) throws IdInvalidException {
        // Lấy lịch sử thi đấu hiện tại
        SparringHistory sparringHistory = getSparringHistoryById(sparringHistoryDTO.getIdSparringHistory());
        if (sparringHistory == null) {
            throw new IdInvalidException("SparringHistory not found");
        }

        // Đánh dấu đã thắng
        sparringHistory.setHasWon(true);
        sparringHistoryRepository.save(sparringHistory);

        // Lấy target node cha
        Integer parentNode = Optional.ofNullable(
                bracketNodeService.getBracketNodeByParticipantsAndChildNodeId(participants, sparringHistory.getTargetNode())
        ).map(BracketNode::getParentNodeId).orElse(null);

        // Đánh dấu thua cho đối thủ cùng parentNode(nếu có)
        List<SparringHistory> siblings = sparringHistoryRepository.findAllByTargetNodeAndIdSparringCombination(
                sparringHistory.getTargetNode(),
                sparringHistory.getSparringList().getSparringCombination().getIdSparringCombination()
        );

        for (SparringHistory sibling : siblings) {
            if (!sibling.getIdSparringHistory().equals(sparringHistory.getIdSparringHistory())) {
                sibling.setHasWon(false);
                sparringHistoryRepository.save(sibling);

                if (sparringHistory.getLevelNode() == 2) {
                    SparringHistory loseSparringHistory = new SparringHistory();
                    loseSparringHistory.setLevelNode(-1);
                    loseSparringHistory.setSourceNode(sibling.getTargetNode());
                    loseSparringHistory.setTargetNode(-1);
                    loseSparringHistory.setSparringList(sibling.getSparringList());
                    sparringHistoryRepository.save(loseSparringHistory);
                }
            }
        }

        if ((sparringHistoryDTO.getNodeInfo().getTargetNode() != 0 && sparringHistoryDTO.getNodeInfo().getTargetNode() != -1)
                && parentNode == null) {
            throw new IdInvalidException("Parent node not found for targetNode " + sparringHistory.getTargetNode());
        }

        // Tạo node mới cho vòng tiếp theo
        SparringHistory winSparringHistory = new SparringHistory();
        winSparringHistory.setLevelNode(sparringHistory.getLevelNode() - 1);
        winSparringHistory.setSourceNode(sparringHistory.getTargetNode());
        winSparringHistory.setTargetNode(parentNode);

        // Sao chép thông tin liên kết
        winSparringHistory.setSparringList(sparringHistory.getSparringList());

        sparringHistoryRepository.save(winSparringHistory);

        return winSparringHistory.getIdSparringHistory().toString();
    }

    /**
     * Xóa một bản ghi SparringHistory và cập nhật trạng thái các node con liên quan.
     *
     * Quy trình:
     * 1. Lấy thông tin SparringHistory cần xóa.
     * 2. Tìm tất cả node con (child nodes) trong cây bracket tương ứng.
     * 3. Lấy toàn bộ SparringHistory trong cùng tổ hợp (combination).
     * 4. Tạo Map để truy cập nhanh theo childNodeId.
     * 5. Cập nhật trạng thái hasWon=false cho các node con.
     * 6. Cuối cùng, xóa bản ghi hiện tại.
     *
     * @param participants        Số lượng người tham gia (để xác định bracket).
     * @param idSparringHistory    ID của bản ghi cần xóa.
     * @throws IdInvalidException Nếu không tìm thấy bản ghi hoặc dữ liệu không hợp lệ.
     */
    public void deleteSparringHistory(int participants, String idSparringHistory) throws IdInvalidException {

        // 1️⃣ Lấy ra bản ghi SparringHistory cần xóa
        SparringHistory sparringHistory = getSparringHistoryById(idSparringHistory);

        // 2️⃣ Lấy danh sách các child node ID (các node con của source node hiện tại)
        List<Integer> childNodeIds = bracketNodeService
                .getBracketNodeByParticipantsAndParentNodeId(participants, sparringHistory.getSourceNode())
                .stream()
                .map(BracketNode::getChildNodeId)
                .toList();

        // 3️⃣ Lấy toàn bộ danh sách SparringHistory thuộc cùng tổ hợp (combination)
        List<SparringHistory> combination = sparringHistoryRepository.findAllBySparringCombination(
                sparringHistory.getSparringList().getSparringCombination());

        // 4️⃣ Tạo map để tra cứu nhanh SparringHistory theo targetNode (childNodeId)
        Map<Integer, SparringHistory> sparringHistoryMap = combination.stream()
                .collect(Collectors.toMap(
                        SparringHistory::getSourceNode,       // key: childNodeId (sourceNode)
                        Function.identity(),                  // value: đối tượng SparringHistory
                        (existing, replacement) -> existing    // giữ bản ghi đầu tiên nếu trùng key
                ));

        // 5️⃣ Duyệt qua từng child node, cập nhật trạng thái hasWon=false
        for (Integer childNodeId : childNodeIds) {
            SparringHistory childHistory = sparringHistoryMap.get(childNodeId);

            if (childHistory == null) {
                // Nếu không tìm thấy, ném lỗi rõ ràng
//                throw new IdInvalidException("Không tìm thấy SparringHistory cho childNodeId: " + childNodeId);
                log.warn("Không tìm thấy SparringHistory cho childNodeId: {}", childNodeId);
                continue;
            }

            // Cập nhật trạng thái thắng thành false
            childHistory.setHasWon(false);
            sparringHistoryRepository.save(childHistory);
        }

        // 6️⃣ Xóa node cha nếu node hiện tại chiến thắng
        SparringHistory parentSparringHistory = sparringHistoryMap.get(sparringHistory.getTargetNode());
        if (parentSparringHistory != null) {
            sparringHistoryRepository.delete(parentSparringHistory);
        }

        // 7️⃣ Xóa bản ghi gốc
        sparringHistoryRepository.delete(sparringHistory);
    }
}
