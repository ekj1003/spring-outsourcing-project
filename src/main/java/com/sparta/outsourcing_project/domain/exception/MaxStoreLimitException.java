package com.sparta.outsourcing_project.domain.exception;

public class MaxStoreLimitException extends RuntimeException {
    public MaxStoreLimitException(String message) {
        super(message);
    }
}
