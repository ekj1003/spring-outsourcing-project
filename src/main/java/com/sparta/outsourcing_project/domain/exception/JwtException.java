package com.sparta.outsourcing_project.domain.exception;

public class JwtException extends RuntimeException{
    public JwtException(String message) {
        super(message);
    }
}
