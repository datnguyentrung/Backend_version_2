package com.dat.backend_version_2.domain.authentication;

import com.dat.backend_version_2.domain.authz.Roles;
import com.dat.backend_version_2.enums.authentication.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
        name = "Users", schema = "authentication",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_account"}),
                @UniqueConstraint(columnNames = {"email"})
        }
)
public abstract class Users {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idUser;

    @Column(nullable = false, name = "id_account")
    private String idAccount;
    private String email;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private LocalDateTime nextPasswordChangeAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserTokens> userTokens = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role", referencedColumnName = "idRole")
    @JsonIgnore
    private Roles role;
}
