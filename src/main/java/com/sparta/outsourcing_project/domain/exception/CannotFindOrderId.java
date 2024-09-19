package com.sparta.outsourcing_project.domain.exception;

public class CannotFindOrderId extends RuntimeException {
    public CannotFindOrderId() {
        super("order의 아이디를 찾을 수 없습니다.");
    }
}
