package com.example.damataxi.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "C001", "Internal Server Error"),
    INVALID_INPUT_VALUE(400, "C002", "Invalid Input Value"),

    INVALID_TOKEN(401, "A001", "Invalid Token"),

    USER_NOT_FOUND(404, "S001", "User Not Found");

    private final int status;
    private final String code;
    private final String message;

}
