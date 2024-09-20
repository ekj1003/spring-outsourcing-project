package com.sparta.outsourcing_project.domain.exception;

public class CannotFindStoreException extends RuntimeException {
  public CannotFindStoreException() {
    super("Store를 찾을 수 없습니다.");
  }
}