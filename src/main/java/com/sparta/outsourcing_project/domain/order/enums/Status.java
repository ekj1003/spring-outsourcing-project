package com.sparta.outsourcing_project.domain.order.enums;

import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sun.jdi.request.InvalidRequestStateException;

import java.util.Arrays;

public enum Status {
    ORDERED,
    ACCEPTED,
    ON_DELIVERY,
    COMPLETED;

    public static Status of(String status) {
        return Arrays.stream(Status.values())
                .filter(r -> r.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestStateException("유효하지 않은 Status 입니다."));
    }
}
