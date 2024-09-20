package com.sparta.outsourcing_project.domain.user.enums;

import com.sun.jdi.request.InvalidRequestStateException;

import java.util.Arrays;

public enum UserType {
    CUSTOMER,
    OWNER;

    public static UserType of(String type) {
        return Arrays.stream(UserType.values())
                .filter(r -> r.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestStateException("유효하지 않은 UserType 입니다."));
    }
}