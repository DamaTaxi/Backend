package com.example.damataxi.global.error.exception;

import lombok.Getter;

@Getter
public class ApiRequestException extends RuntimeException {

    private int status;
    private String message;

    public ApiRequestException(int status,String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

}
