package com.wishlist.challenge.config;

import com.wishlist.challenge.config.exception.BusinessException;
import com.wishlist.challenge.config.exception.BusinessExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusinessExceptionHandlerTest {

    @Test
    void testHandleBusinessException() {
        BusinessExceptionHandler handler = new BusinessExceptionHandler();
        String errorMessage = "Test error message";
        BusinessException exception = new BusinessException(errorMessage);

        ResponseEntity<String> response = handler.handleBusinessException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }
}
