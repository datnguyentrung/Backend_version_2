package com.dat.backend_version_2.domain.training;

import com.dat.backend_version_2.dto.training.StudentClassSession.StudentClassSessionId;
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
@Table(name = "StudentClassSession",
        schema = "training")
@IdClass(StudentClassSessionId.class)
public class StudentClassSession {
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
    private Student student;

    @ManyToOne
    @MapsId("idClassSession")
    @JoinColumn(name = "idClassSession", referencedColumnName = "idClassSession")
    @JsonIgnore
    private ClassSession classSession;
}
