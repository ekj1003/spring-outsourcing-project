package com.sparta.outsourcing_project.domain.exception;

public class CannotFindOrderException extends RuntimeException {
    public CannotFindOrderException() {
        super("주문을 찾을 수 없습니다.");
    }
}
