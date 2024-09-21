package com.sparta.outsourcing_project.domain.exception;

public class CannotFindNoticeIdException extends RuntimeException {
    public CannotFindNoticeIdException() { super("해당 공지를 찾을 수 없습니다.");}
}
