package com.example.damataxi.global.error.exception;

import com.example.damataxi.global.error.ErrorCode;

public class InvalidInputValueException extends GlobalException{
    public InvalidInputValueException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
