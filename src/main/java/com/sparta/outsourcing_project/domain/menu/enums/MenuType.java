package com.sparta.outsourcing_project.domain.menu.enums;

import com.sun.jdi.request.InvalidRequestStateException;

import java.util.Arrays;

public enum MenuType {
    KOREAN,
    JAPANESE,
    WESTERN,
    CHINESE;

    public static MenuType of(String type) {
        return Arrays.stream(MenuType.values())
                .filter(r -> r.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestStateException("유효하지 않은 MenuType 입니다."));
    }
}