package com.enigma.nutrismartbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class VoucherInvalidException extends RuntimeException{
    public VoucherInvalidException() {
        super("Voucher is invalid");
    }
}
