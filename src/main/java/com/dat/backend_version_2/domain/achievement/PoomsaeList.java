package com.dat.backend_version_2.domain.achievement;

import com.dat.backend_version_2.domain.tournament.Poomsae.PoomsaeCombination;
import com.dat.backend_version_2.domain.tournament.Tournament;
import com.dat.backend_version_2.domain.training.Branch;
import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.enums.tournament.AgeDivision;
import com.dat.backend_version_2.enums.achievement.Medal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PoomsaeList", schema = "achievement")
public class PoomsaeList {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idPoomsaeList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id_user")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament")
    private Tournament tournament;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poomsae_combination")
    private PoomsaeCombination poomsaeCombination;

    @Enumerated(EnumType.STRING)
    private Medal medal;

    @Enumerated(EnumType.STRING)
    private AgeDivision ageDivision;

    @PrePersist
    @PreUpdate
    public void initFields() {
        if (student != null) {
            this.branch = student.getBranch();
            this.ageDivision = AgeDivision.fromBirthDate(student.getBirthDate());
        }
    }
}