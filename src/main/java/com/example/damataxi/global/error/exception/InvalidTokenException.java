package com.example.damataxi.global.error.exception;

import com.example.damataxi.global.error.ErrorCode;

public class InvalidTokenException extends GlobalException{

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}