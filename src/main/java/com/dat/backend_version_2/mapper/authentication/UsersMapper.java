package com.dat.backend_version_2.mapper.authentication;

import com.dat.backend_version_2.domain.authentication.Users;
import com.dat.backend_version_2.dto.authentication.LoginRes;

public class UsersMapper {
    public LoginRes.UserLogin toUserLogin(Users user) {
        if (user == null) {
            return null;
        }
        LoginRes.UserLogin userLogin = new LoginRes.UserLogin();
        userLogin.setRole(user.getRole().getIdRole());
        userLogin.setStatus(user.getStatus());

        return userLogin;
    }
}
