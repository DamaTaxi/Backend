package com.example.damataxi.global.error.exception;

import com.example.damataxi.global.error.ErrorCode;

public class NotCreatorException extends GlobalException{
    public NotCreatorException(String username){
        super(username + "is not creator", ErrorCode.NOT_CREATOR);
    }
}
