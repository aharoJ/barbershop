package io.aharoj.barbershop_backend.modules.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.aharoj.barbershop_backend.modules.customer.exception.CustomerProfileAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Customer
    @ExceptionHandler(CustomerProfileAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomerProfileAlreadyExists(CustomerProfileAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Conflict", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * 
     * 
    // Barber MOFIDY ONCE WE GET TO BARBER 
    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBarberProfileAlreadyExists(ProfileAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Conflict", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // Owner  MOFIDY ONCE WE GET TO OWNER
    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleOwnerProfileAlreadyExists(ProfileAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Conflict", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    */

    // A simple POJO for the error response
    public static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}