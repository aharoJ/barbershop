package io.aharoj.barbershop_backend.modules.barber.exception;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BarberProfileAlreadyExistsException extends RuntimeException {
    public BarberProfileAlreadyExistsException(String message) {
        super(message);
    }
}
