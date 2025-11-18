package com.dat.backend_version_2.domain.registration;

import com.dat.backend_version_2.domain.training.Branch;
import com.dat.backend_version_2.enums.registration.RegistrationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Registration", schema = "registration")
public class Registration {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idRegistration;

    private LocalDate birthDate;
    private String name;
    private String phone;

    private String referredBy;

    @ManyToOne
    @JoinColumn(name = "branch", nullable = true)
    private Branch branch;
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus registrationStatus = RegistrationStatus.ENROLLED;
}
