package com.dat.backend_version_2.domain.training;

import com.dat.backend_version_2.domain.authentication.Users;
import com.dat.backend_version_2.enums.training.Student.Member;
import com.dat.backend_version_2.enums.training.BeltLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "idUser") // chỉ định PK join với Users.idUser
@Table(name = "Student", schema = "training")
public class Student extends Users {
    private String idNational;

    @Column(nullable = false)
    private Boolean isActive = true;
    private String name;
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "branch", referencedColumnName = "idBranch")
    @JsonIgnore
    private Branch branch;

    @Enumerated(EnumType.STRING)
    private BeltLevel beltLevel = BeltLevel.C10;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<StudentClassSession> studentClassSessions = new ArrayList<>();

    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Member member;

    private String phone;
}
