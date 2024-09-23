package com.sparta.outsourcing_project.domain.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class AllExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message);
        responseBody.put("status", status.value());
        responseBody.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(status).body(responseBody);
    }

    @ExceptionHandler(CannotFindReviewIdException.class)
    public ResponseEntity<Map<String, Object>> ConnotFindReviewId(CannotFindReviewIdException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CannotFindOrderException.class)
    public ResponseEntity<Map<String, Object>> cannotFindOrderId(CannotFindOrderException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CannotFindStoreIdException.class)
    public ResponseEntity<Map<String, Object>> cannotFindStoreIdException(CannotFindStoreIdException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> authException(AuthException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxStoreLimitException.class)
    public ResponseEntity<Map<String, Object>> maxStoreLimitException(MaxStoreLimitException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> tokenException(JwtException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> exception(UserRequestException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> authenticationFailedException(AuthenticationFailedException ex) {
        return buildResponse("잘못된 아이디 또는 비밀번호입니다.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> cannotFindMenuException(CannotFindMenuException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> cannotFindStoreException(CannotFindStoreException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> ownerNotAuthorizedException(OwnerNotAuthorizedException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotOpenException.class)
    public ResponseEntity<Map<String, Object>> notOpenException(NotOpenException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotArrivedException.class)
    public ResponseEntity<Map<String, Object>> notArrivedException(NotArrivedException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NotMinPriceException.class)
    public ResponseEntity<Map<String, Object>> notMinPriceException(NotMinPriceException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception ex) {
        log.error(ex + ": " +ex.getMessage());
        // e.printStackTrace()
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
