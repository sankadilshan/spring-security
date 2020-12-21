package com.sankadilshan.springsecurity.enums;

import lombok.Getter;

@Getter
public enum Permissions {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write");

    private String permission;

    Permissions(String permission) {
        this.permission=permission;
    }


}

