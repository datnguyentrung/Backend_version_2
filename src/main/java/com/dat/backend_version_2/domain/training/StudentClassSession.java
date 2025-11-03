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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", referencedColumnName = "idUser", insertable = false, updatable = false)
    @JsonIgnore
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idClassSession", referencedColumnName = "idClassSession", insertable = false, updatable = false)
    @JsonIgnore
    private ClassSession classSession;
}
