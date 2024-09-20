package com.sparta.outsourcing_project.domain.exception;

public class CannotFindOrderIdException extends RuntimeException {
    public CannotFindOrderIdException() {
        super("주문의 Id를 찾을 수 없습니다.");
    }
}
