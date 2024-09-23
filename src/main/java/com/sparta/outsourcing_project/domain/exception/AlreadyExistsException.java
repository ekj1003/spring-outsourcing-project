package com.sparta.outsourcing_project.domain.exception;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException() {
        super("장바구니에 이미 존재합니다.");
    }
}
