package com.enigma.nutrismartbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AlreadyPurchasedException extends RuntimeException{
    public AlreadyPurchasedException() {
        super("Product already purchased");
    }
}
