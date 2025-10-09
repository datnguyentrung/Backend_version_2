package com.dat.backend_version_2.domain.authentication;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserTokens", schema = "authentication")
public class UserTokens {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, nullable = false)
    private UUID idToken;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    private Users user;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    private String idDevice; // Id ổn định trên mỗi device/app

    @Column(nullable = false)
    private Boolean revoked = false;

    @Column(nullable = false)
    private LocalDateTime expiresAt;
}
