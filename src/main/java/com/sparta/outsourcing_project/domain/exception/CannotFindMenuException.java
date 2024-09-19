package com.sparta.outsourcing_project.domain.exception;

public class CannotFindMenuException extends RuntimeException {
    public CannotFindMenuException() {
        super("Menu를 찾을 수 없습니다.");
    }
}