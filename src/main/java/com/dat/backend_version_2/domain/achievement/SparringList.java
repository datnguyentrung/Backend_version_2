package com.dat.backend_version_2.domain.achievement;

import com.dat.backend_version_2.domain.tournament.Sparring.SparringCombination;
import com.dat.backend_version_2.domain.tournament.Tournament;
import com.dat.backend_version_2.domain.training.Branch;
import com.dat.backend_version_2.domain.training.Student;
import com.dat.backend_version_2.enums.achievement.Medal;
import com.dat.backend_version_2.enums.tournament.AgeDivision;
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
@Table(name = "SparringList", schema = "achievement")
public class SparringList {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idSparringList;

    @ManyToOne
    @JoinColumn(name = "student_id_user")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "tournament")
    private Tournament tournament;

    @Enumerated(EnumType.STRING)
    private Medal medal;

    @Enumerated(EnumType.STRING)
    private AgeDivision ageDivision;

    @ManyToOne
    @JoinColumn(name = "branch")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "sparring_combination")
    private SparringCombination sparringCombination;

    @PrePersist
    @PreUpdate
    public void initFields() {
        if (student != null) {
            this.branch = student.getBranch();
            this.ageDivision = AgeDivision.fromBirthDate(student.getBirthDate());
        }
    }
}
