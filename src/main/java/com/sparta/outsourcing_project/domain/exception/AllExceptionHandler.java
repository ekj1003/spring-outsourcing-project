package com.sparta.outsourcing_project.domain.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(CannotFindOrderId.class)
    public ResponseEntity<Map<String, Object>> CannotFindOrderId(CannotFindOrderId ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        responseBody.put("status", HttpStatus.NOT_FOUND.value());
        responseBody.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    @ExceptionHandler(CannotFindMenuException.class)
    public ResponseEntity<Map<String, Object>> handleCannotFindMenuException(CannotFindMenuException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        responseBody.put("status", HttpStatus.NOT_FOUND.value());
        responseBody.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    @ExceptionHandler(CannotFindStoreException.class)
    public ResponseEntity<Map<String, Object>> handleCannotFindStoreException(CannotFindStoreException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        responseBody.put("status", HttpStatus.NOT_FOUND.value());
        responseBody.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    @ExceptionHandler(OwnerNotAuthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleOwnerNotAuthorizedException(OwnerNotAuthorizedException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        responseBody.put("status", HttpStatus.FORBIDDEN.value());
        responseBody.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception ex) {
        log.error(ex + ": " +ex.getMessage());
        // e.printStackTrace()
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
