package com.enigma.nutrismartbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String id, Class object) {
        super("Resource "+id+" of class "+object+ " cannot found");
    }
}
