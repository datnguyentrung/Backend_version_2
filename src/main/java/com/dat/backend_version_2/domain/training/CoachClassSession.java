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
    @MapsId("idUser")
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @JsonIgnore
    private Coach coach;

    @ManyToOne
    @MapsId("idClassSession")
    @JoinColumn(name = "classSession", referencedColumnName = "idClassSession")
    @JsonIgnore
    private ClassSession classSession;
}