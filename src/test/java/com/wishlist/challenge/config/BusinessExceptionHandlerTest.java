package com.wishlist.challenge.config;

import com.wishlist.challenge.config.exception.BusinessDuplicatedException;
import com.wishlist.challenge.config.exception.BusinessException;
import com.wishlist.challenge.config.handler.BusinessExceptionHandler;
import com.wishlist.challenge.config.exception.BusinessNotFoundException;
import com.wishlist.challenge.config.exception.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusinessExceptionHandlerTest {

    private static final BusinessExceptionHandler HANDLER = new BusinessExceptionHandler();

    @Test
    void testHandleBusinessException() {
        String errorMessage = "validation failed";
        BusinessException exception = new BusinessException(errorMessage);

        ResponseEntity<ErrorResponse> response = HANDLER.handleBusiness(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    void testHandleBusinessNotFoundException() {
        String errorMessage = "Resource not found";
        BusinessNotFoundException exception = new BusinessNotFoundException(errorMessage);

        ResponseEntity<ErrorResponse> response = HANDLER.handleNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    }

    @Test
    void testHandleBusinessDuplicatedException() {
        String errorMessage = "duplicated product";
        BusinessDuplicatedException exception = new BusinessDuplicatedException(errorMessage);

        ResponseEntity<ErrorResponse> response = HANDLER.handleDuplicate(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
    }

    @Test
    void testHandleInternalError() {
        String errorMessage = "Internal Server Error";
        Exception exception = new Exception();

        ResponseEntity<ErrorResponse> response = HANDLER.handleInternalError(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }
}
