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
        indexes = {
                @Index(name = "idx_class_session", columnList = "id_class_session"),
        },
        schema = "training")
@IdClass(StudentClassSessionId.class)
public class StudentClassSession {
    @Id
    @Column(name = "id_user")
    private UUID idUser;

    @Id
    @Column(name = "id_class_session")
    private String idClassSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    @JsonIgnore
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_class_session", insertable = false, updatable = false)
    @JsonIgnore
    private ClassSession classSession;
}
