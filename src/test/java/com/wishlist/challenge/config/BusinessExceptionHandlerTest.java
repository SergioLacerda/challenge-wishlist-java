package com.wishlist.challenge.config;

import com.wishlist.challenge.config.exception.BusinessDuplicatedException;
import com.wishlist.challenge.config.exception.BusinessException;
import com.wishlist.challenge.config.exception.BusinessExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusinessExceptionHandlerTest {

    private static final BusinessExceptionHandler handler = new BusinessExceptionHandler();

    @Test
    void testHandleBusinessException() {
        String errorMessage = "Test error message";
        BusinessException exception = new BusinessException(errorMessage);

        ResponseEntity<String> response = handler.handleBusinessException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    void testHandleBusinessDuplicatedException() {
        String errorMessage = "duplicated product";
        BusinessDuplicatedException exception = new BusinessDuplicatedException(errorMessage);

        ResponseEntity<String> response = handler.handleBusinessDuplicatedException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }
}
