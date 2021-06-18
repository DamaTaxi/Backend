package com.example.damataxi.global.error.exception;

import com.example.damataxi.global.error.ErrorCode;

public class ApplyNotFoundException extends GlobalException{
    public ApplyNotFoundException(String username) {
        super(username + " did not apply", ErrorCode.APPLY_NOT_FOUND);
    }
}
