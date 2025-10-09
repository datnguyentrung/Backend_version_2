package com.dat.backend_version_2.dto.training.CoachClassSession;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoachClassSessionId {
    private UUID idUser;
    private String idClassSession;
}
