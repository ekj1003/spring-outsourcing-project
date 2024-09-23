package com.sparta.outsourcing_project.domain.exception;

public class CannotFindShoppingCartException extends RuntimeException {
    public CannotFindShoppingCartException() {
        super("장바구니에서 찾을 수 없습니다.");
    }
}
