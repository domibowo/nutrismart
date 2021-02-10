package com.enigma.nutrismartbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InactiveAccountException extends RuntimeException{
    public InactiveAccountException() {
        super("Forbidden area");
    }
}
