package com.example.damataxi.global.error.exception;

import com.example.damataxi.global.error.ErrorCode;

public class TaxiPotNotFoundException extends GlobalException{

    public TaxiPotNotFoundException() {
        super(ErrorCode.TAXI_POT_NOT_FOUND);
    }

    public TaxiPotNotFoundException(int id) {
        super("taxi pot " + id + "is not found", ErrorCode.TAXI_POT_NOT_FOUND);
    }
}
