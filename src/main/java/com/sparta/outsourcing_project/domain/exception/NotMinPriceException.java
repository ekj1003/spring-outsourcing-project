package com.sparta.outsourcing_project.domain.exception;

public class NotMinPriceException extends RuntimeException {
    public NotMinPriceException(int minPrice, int price) {
        super("최저금액 이상 주문해야 합니다." + " 최저금액: " + minPrice + ", 현재 금액: " + price);
    }
}
