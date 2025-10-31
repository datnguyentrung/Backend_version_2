package com.dat.backend_version_2.dto.arcFace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FaceRecognitionDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private boolean success;
        private boolean recognized;
        private String idCoach;
        private Boolean status;
        private Double confidence;
        private String message;
    }
}
