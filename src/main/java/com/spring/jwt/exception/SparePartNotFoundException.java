package com.spring.jwt.exception;

public class SparePartNotFoundException extends RuntimeException {
    public SparePartNotFoundException(String message) {
        super(message);
    }
}