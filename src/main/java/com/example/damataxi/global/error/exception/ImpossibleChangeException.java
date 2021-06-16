package com.example.damataxi.global.error.exception;

import com.example.damataxi.global.error.ErrorCode;

public class ImpossibleChangeException extends GlobalException{
    public ImpossibleChangeException(){
        super(ErrorCode.IMPOSSIBLE_CHANGE);
    }
}
