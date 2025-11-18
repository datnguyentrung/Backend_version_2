package com.dat.backend_version_2.service.authentication;

import com.dat.backend_version_2.domain.authz.Roles;
import com.dat.backend_version_2.domain.authentication.Users;
import com.dat.backend_version_2.dto.authentication.ChangePasswordReq;
import com.dat.backend_version_2.enums.authentication.UserStatus;
import com.dat.backend_version_2.repository.authentication.UsersRepository;
import com.dat.backend_version_2.service.authz.RolesService;
import com.dat.backend_version_2.util.AccountUtil;
import com.dat.backend_version_2.util.error.InvalidPasswordException;
import com.dat.backend_version_2.util.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesService rolesService;

    @Value("${password.time_password_change_days}")
    private long timePasswordChange;

    public String getLastIdAccountByGeneration(String generation, Roles role) {
        String roleKey = AccountUtil.getRoleKey(role);
        String prefix = String.format("TKD%sG%s", generation, roleKey);

        return Optional.ofNullable(
                        userRepository.findFirstByIdAccountStartingWithOrderByIdAccountDesc(prefix)
                )
                .map(Users::getIdAccount)
                .orElse(null); // hoặc throw exception tùy nghiệp vụ
    }

    public String generateNewIdAccount(String generation, Roles role) {
        // Tìm ID account cuối cùng theo thế hệ + role
        String lastIdAccount = getLastIdAccountByGeneration(
                generation,
                role
        );

        // Tính số thứ tự mới
        int sequenceNumber = Optional.ofNullable(lastIdAccount)
                .map(id -> id.substring(id.length() - 3))  // lấy 3 ký tự cuối
                .map(Integer::parseInt)
                .map(n -> n + 1)
                .orElse(1);

        // Generate account ID mới
        return AccountUtil.generateIdAccount(role, sequenceNumber);
    }

    // Hàm dùng chung để gán thông tin User
    public <T extends Users> void setupBaseUser(T user, String roleName) {
        Roles role = rolesService.getRoleById(roleName);
        String idAccount = generateNewIdAccount(AccountUtil.getGenCurrent(), role);

        user.setRole(role);
        user.setIdAccount(idAccount);
        user.setPasswordHash(passwordEncoder.encode("123456"));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(LocalDate.now());
    }

    // Lấy user theo idAccount (dành cho authentication)
    public Users getUserByIdAccount(String idAccount) {
        return (Users) userRepository.findByIdAccount(idAccount)
                .orElseThrow(() -> new UserNotFoundException("User not found with idAccount: " + idAccount));
    }

    // Lấy user theo idUser
    public Users getUserById(String idUser) {
        return userRepository.findById(UUID.fromString(idUser))
                .orElseThrow(() -> new UserNotFoundException("User not found with idUser: " + idUser));
    }

    public void changePassword(String idUser, ChangePasswordReq passwordReq) {
        Users user = getUserById(idUser);

        if (!passwordEncoder.matches(passwordReq.getOldPassword(), user.getPasswordHash())) {
            throw new InvalidPasswordException("Mật khẩu cũ không đúng");
        }

        if (!passwordReq.getNewPassword().equals(passwordReq.getConfirmPassword())) {
            throw new InvalidPasswordException("Xác nhận mật khẩu không khớp");
        }

        user.setUpdatedAt(LocalDateTime.now());
        user.setNextPasswordChangeAt(LocalDateTime.now().plusDays(timePasswordChange));

        user.setPasswordHash(passwordEncoder.encode(passwordReq.getNewPassword()));
        userRepository.save(user);
    }
}
