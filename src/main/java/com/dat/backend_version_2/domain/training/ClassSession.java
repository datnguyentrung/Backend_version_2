package com.dat.backend_version_2.domain.training;

import com.dat.backend_version_2.enums.training.ClassSession.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ClassSession",
        schema = "training")
@NoArgsConstructor
@AllArgsConstructor
public class ClassSession {
    @Id
    private String idClassSession;

    @Enumerated(EnumType.STRING)
    private ClassLevel classLevel;

    @Enumerated(EnumType.STRING)
    private Location location;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    @Enumerated(EnumType.STRING)
    private Weekday weekday;

    @Enumerated(EnumType.STRING)
    private Session session;

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "branch", referencedColumnName = "idBranch")
    private Branch branch;

    @OneToMany(mappedBy = "classSession", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<CoachClassSession> coachClassSessions = new ArrayList<>();

    @OneToMany(mappedBy = "classSession", cascade = CascadeType.REMOVE, orphanRemoval = true) // ✅ SỬA ĐỔI: Chỉ giữ lại REMOVE
    @JsonIgnore
    private List<StudentClassSession> studentClassSessions = new ArrayList<>();
}
