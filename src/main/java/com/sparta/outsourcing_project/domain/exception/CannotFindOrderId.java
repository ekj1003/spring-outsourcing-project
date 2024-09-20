package com.sparta.outsourcing_project.domain.exception;

public class CannotFindOrderId extends RuntimeException {
    public CannotFindOrderId() {
        super("주문의 Id를 찾을 수 없습니다.");
    }
}
