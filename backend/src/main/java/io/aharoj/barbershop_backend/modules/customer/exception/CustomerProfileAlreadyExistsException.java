package io.aharoj.barbershop_backend.modules.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerProfileAlreadyExistsException extends RuntimeException {
    public CustomerProfileAlreadyExistsException(String message) {
        super(message);
    }
}
