package com.sparta.outsourcing_project.domain.exception;

public class CannotFindReviewIdException extends RuntimeException{
    public CannotFindReviewIdException(){
        super("리뷰를 찾을 수 없습니다.");
    }
}
