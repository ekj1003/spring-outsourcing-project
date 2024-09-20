package com.sparta.outsourcing_project.domain.exception;

public class NotOpenException extends RuntimeException{
    public NotOpenException() {
        super("엽업시간이 아닙니다.");
    }
}
