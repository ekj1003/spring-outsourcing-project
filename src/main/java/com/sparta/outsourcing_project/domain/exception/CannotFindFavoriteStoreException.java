package com.sparta.outsourcing_project.domain.exception;

public class CannotFindFavoriteStoreException extends RuntimeException {
    public CannotFindFavoriteStoreException() {super("해당 즐겨찾기는 존재하지 않습니다.");}
}
