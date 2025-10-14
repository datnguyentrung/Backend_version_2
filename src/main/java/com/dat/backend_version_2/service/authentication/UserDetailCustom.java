package com.dat.backend_version_2.service.authentication;

import com.dat.backend_version_2.domain.authentication.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("userDetailsService")
public class UserDetailCustom implements UserDetailsService {
    @Autowired
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String idAccount) throws UsernameNotFoundException {
        Users user = usersService.getUserByIdAccount(idAccount);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with idAccount: " + idAccount);
        }
        return new User(
                user.getIdAccount(),
                user.getPasswordHash(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}