package com.dat.backend_version_2.domain.training;

import com.dat.backend_version_2.domain.authentication.Users;
import com.dat.backend_version_2.enums.training.BeltLevel;
import com.dat.backend_version_2.enums.training.Coach.Position;
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
@Table(name = "Coach", schema = "training")
public class Coach extends Users {
    private Boolean isActive;
    private String name;
    private LocalDate birthDate;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private BeltLevel beltLevel;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoachClassSession> coachClassSessions = new ArrayList<>();
}