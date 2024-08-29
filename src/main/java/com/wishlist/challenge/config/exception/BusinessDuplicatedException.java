package com.wishlist.challenge.config.exception;

public class BusinessDuplicatedException extends RuntimeException {
    public BusinessDuplicatedException(String message) {
        super(message);
    }
}