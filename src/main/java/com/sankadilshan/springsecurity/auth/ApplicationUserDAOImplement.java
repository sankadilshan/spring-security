package com.sankadilshan.springsecurity.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.sankadilshan.springsecurity.enums.Roles.*;

@Repository("fake")
public class ApplicationUserDAOImplement implements ApplicationUserDAO{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUser()
                .stream()
                .filter(user-> username.equals(user.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUser(){
        return Arrays.asList(
                new ApplicationUser(
                        ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("user"),
                        "admin",
                        true,
                        true,
                        true,
                        true),

                new ApplicationUser(
                        STUDENT_LEAD.getGrantedAuthorities(),
                        passwordEncoder.encode("user"),
                        "student",
                        true,
                        true,
                        true,
                        true),

                new ApplicationUser(
                        ADMIN_W.getGrantedAuthorities(),
                        passwordEncoder.encode("user"),
                        "admin-w",
                        true,
                        true,
                        true,
                        true
                )
        );

    }
}
