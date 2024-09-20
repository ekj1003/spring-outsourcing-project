package com.sparta.outsourcing_project.domain.exception;

public class CannotFindReviewId extends RuntimeException{
    public CannotFindReviewId(){
        super("리뷰를 찾을 수 없습니다.");
    }
}
