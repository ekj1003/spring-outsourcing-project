package com.sparta.outsourcing_project.domain.exception;

public class NotArrivedException extends RuntimeException {
    public NotArrivedException() {
        super("아직 음식이 도착하지 않았습니다.");
    }
}
