package com.sankadilshan.springsecurity.auth;

import com.sankadilshan.springsecurity.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("fake1")
public class ApplicationDAODemo implements ApplicationUserDAO{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        ApplicationUser applicationUser = new ApplicationUser(
                Roles.ADMIN.getGrantedAuthorities(),
                passwordEncoder.encode("admin"),
                "admin",
                true,
                true,
                true,
                true
        );
        return Optional.of(applicationUser);
    }
}
