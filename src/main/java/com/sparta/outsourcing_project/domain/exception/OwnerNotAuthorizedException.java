package com.sparta.outsourcing_project.domain.exception;

public class OwnerNotAuthorizedException extends RuntimeException {
    public OwnerNotAuthorizedException() {
        super("user는 이 작업을 수행할 권한이 없습니다.");
    }
}