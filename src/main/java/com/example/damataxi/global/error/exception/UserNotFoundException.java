package com.example.damataxi.global.error.exception;

import com.example.damataxi.global.error.ErrorCode;

public class UserNotFoundException extends GlobalException{

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(String username) {
        super("username " + username + " is not found", ErrorCode.USER_NOT_FOUND);
    }
}
