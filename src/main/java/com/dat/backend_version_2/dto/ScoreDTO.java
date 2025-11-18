package com.dat.backend_version_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ScoreDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SummaryByQuarter{
        private int year;
        private int quarter;
        private SummaryScore summaryScore;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SummaryScore{
        private ConductScore conductScore;
        private AwarenessScore awarenessScore;
        private BonusScore bonusScore;
        private Double score;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConductScore {
        private Integer absentSession = 0; // số lượng buổi nghỉ (attendance: v)
        private Integer late = 0; // số lượng muộn (attendance: M)
        private Integer excusedAbsence = 0; // số lượng phép (attendance: P)
        private Integer compensatorySession = 0; // số lượng tập bù (attendance: B)
        private Integer trainingSession = 0; // số lượng buổi rèn luyện (attendance: X || B || M)
        private Double score;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AwarenessScore {
        private Integer highScore = 0; // số lượng buổi điểm tốt (evaluation: T)
        private Integer mediumScore = 0; // số lượng buổi điểm trung bình (evaluation: TB)
        private Integer lowScore = 0; // số lượng buổi điểm yếu (evalution: Y)
        private Double score;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BonusScore {
        private Integer trainingScore = 0;
        private ContributionScore contributionScore;
        private AchievementScore achievementScore;
        private Double score;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AchievementScore {
        private Integer skillStrengthScore = 0;
        private Medal medalScore;
        private Double score;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Medal {
        private Integer goldMedal = 0;
        private Integer silverMedal = 0;
        private Integer bronzeMedal = 0;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContributionScore {
        private Integer memberCard = 0;
        private Tuition tuition = new Tuition();
        private Integer video = 0;
        private Double score;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tuition {
        private Integer onTime = 0;
        private Integer late = 0;
    }
}
