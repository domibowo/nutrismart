package com.enigma.nutrismartbe.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ProhibitedAreaException extends RuntimeException{
    public ProhibitedAreaException() {
        super("You cannot access this site!");
    }
}
