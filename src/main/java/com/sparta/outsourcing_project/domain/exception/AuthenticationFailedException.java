package com.sparta.outsourcing_project.domain.exception;

public class AuthenticationFailedException extends RuntimeException{
    public AuthenticationFailedException() {
        super("잘못된 아이디 또는 비밀번호입니다.");
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
