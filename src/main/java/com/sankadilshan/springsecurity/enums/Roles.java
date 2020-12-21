package com.sankadilshan.springsecurity.enums;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sankadilshan.springsecurity.enums.Permissions.*;

@Getter
public enum Roles {

    STUDENT_LEAD(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE)),
    ADMIN(Sets.newHashSet(ADMIN_READ,ADMIN_WRITE)),
    ADMIN_W(Sets.newHashSet(ADMIN_WRITE));

    private final Set<Permissions> permissions;

    Roles(Set<Permissions> permissions) {
        this.permissions=permissions;
    }


    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions =getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return  permissions;
    }


}
