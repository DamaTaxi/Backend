package com.example.damataxi.global.error.exception;

import com.example.damataxi.global.error.ErrorCode;

public class SuggestionNotFoundException extends GlobalException{
    public SuggestionNotFoundException(int id){
        super("suggestion " + id + " is not found", ErrorCode.SUGGESTION_NOT_FOUND);
    }
}
