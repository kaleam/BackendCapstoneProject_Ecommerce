package com.ecommerce.cartservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
     public ResponseEntity<String> handleCartNotFoundException(Exception e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
     }
}
