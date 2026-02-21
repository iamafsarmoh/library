package com.library.exception;

public class PatronNotFoundException extends Exception {
    public PatronNotFoundException(String message) {
        super(message);
    }
}