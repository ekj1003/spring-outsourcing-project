package com.sparta.outsourcing_project.domain.exception;

public class UserRequestException extends RuntimeException {
    public UserRequestException(String message) {
        super(message);
    }
}
