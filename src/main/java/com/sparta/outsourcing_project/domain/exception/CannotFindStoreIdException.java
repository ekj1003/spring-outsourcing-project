package com.sparta.outsourcing_project.domain.exception;

public class CannotFindStoreIdException extends RuntimeException {
    public CannotFindStoreIdException() {
        super("해당 ID의 가게를 찾을 수 없습니다.");
    }
}
