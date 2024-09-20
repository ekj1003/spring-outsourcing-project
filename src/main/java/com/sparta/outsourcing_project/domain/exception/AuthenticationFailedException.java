package com.sparta.outsourcing_project.domain.exception;

public class AuthenticationFailedException extends RuntimeException{
    public AuthenticationFailedException() {
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
