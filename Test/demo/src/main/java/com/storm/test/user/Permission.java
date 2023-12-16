package com.storm.test.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),

    ADMIN_UPDATE("admin:update"),

    ADMIN_CREATE("admin:create"),

    ADMIN_DELETE("admin:delete"),

    ADMIN_SEARCH("admin:search"),

    MANAGER_READ("manager:read"),

    MANAGER_UPDATE("manager:update"),

    MANAGER_CREATE("manager:create"),

    MANAGER_SEARCH("manager:search"),

    MANAGER_DELETE("manager:delete")

    ;

    @Getter
    private final String permission;
}
