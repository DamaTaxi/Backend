package com.example.damataxi.global.error.exception;

import com.example.damataxi.global.error.ErrorCode;

public class AlreadyApplyException extends GlobalException{
    public AlreadyApplyException(){
        super(ErrorCode.ALREADY_APPLY);
    }
}
