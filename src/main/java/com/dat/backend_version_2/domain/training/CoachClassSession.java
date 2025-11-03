package com.dat.backend_version_2.domain.training;

import com.dat.backend_version_2.dto.training.CoachClassSession.CoachClassSessionId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CoachClassSession",
        schema = "training")
@IdClass(CoachClassSessionId.class)
public class CoachClassSession {
    @Id
    @Column(name = "idUser")
    private UUID idUser;

    @Id
    @Column(name = "idClassSession")
    private String idClassSession;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser", insertable = false, updatable = false)
    @JsonIgnore
    private Coach coach;

    @ManyToOne
    @JoinColumn(name = "classSession", referencedColumnName = "idClassSession", insertable = false, updatable = false)
    @JsonIgnore
    private ClassSession classSession;
}