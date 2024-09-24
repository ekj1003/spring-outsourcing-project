package com.sparta.outsourcing_project.domain.user.enums;

import com.sun.jdi.request.InvalidRequestStateException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum UserType {
    ADMIN("/"),
    CUSTOMER("/customers", "/users"),
    OWNER("/owners", "/users");

    private final Set<String> accessiblePaths;

    UserType(String... accessiblePaths) {
        this.accessiblePaths = new HashSet<>(Arrays.asList(accessiblePaths));
    }

    public boolean canAccess(String url) {
        if(this == UserType.ADMIN) return true;
        return accessiblePaths.stream().anyMatch(url::startsWith);
    }

    public static UserType of(String type) {
        return Arrays.stream(UserType.values())
                .filter(r -> r.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestStateException("유효하지 않은 UserType 입니다."));
    }
}